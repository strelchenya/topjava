package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractJpaUtilUserServiceTest extends AbstractUserServiceTest{
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setup() {
        super.setup();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
