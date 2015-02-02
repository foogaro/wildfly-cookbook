package com.packtpub.wildflycookbook.vault;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by lfugaro on 9/22/14.
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserManager {

    @PersistenceContext(unitName = "WildFlyCookBookPU")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(User user) {
        em.persist(user);
    }
}
