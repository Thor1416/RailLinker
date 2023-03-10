package com.railweb.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.railweb.shared.domain.base.AbstractEntity;


@Service
@Transactional
public class LocalPermissionService {

	@Autowired
	private MutableAclService aclService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	public void addPermissionForUser(AbstractEntity<? extends Serializable> target,Permission perm, String username) {
		final Sid sid = new PrincipalSid(username);
		this.addPermissionForSid(target, perm, sid);
	}
	
	private void addPermissionForSid(AbstractEntity<? extends Serializable> target, Permission permission,Sid sid) {
		final TransactionTemplate tt = new TransactionTemplate(transactionManager);
		tt.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				final ObjectIdentity oi = new ObjectIdentityImpl(target.getClass(),target.getId());
				
				MutableAcl acl;
				try {
					acl = (MutableAcl) aclService.readAclById(oi);
				}catch(final NotFoundException nfe) {
					acl = aclService.createAcl(oi);
				}
				acl.insertAce(acl.getEntries().size(), permission, sid, true);
				aclService.updateAcl(acl);
			}
		});
	}
}
