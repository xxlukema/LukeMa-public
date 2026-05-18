package com.learn.shein.psql.service;


import org.springframework.stereotype.Service;

import com.learn.entity.SpringSecurityAuthorityEntity;
import com.learn.entity.SpringSecurityUserEntity;
import com.learn.exception.AppException;
import com.learn.shein.psql.dto.SheinUserDto;
import com.learn.shein.psql.entity.Country;
import com.learn.shein.psql.repository.CountryRepository;
import com.learn.shein.psql.repository.SpringSecurityAuthorityEntityEnum;
import com.learn.shein.psql.repository.SpringSecurityAuthorityEntityRepository;
import com.learn.shein.psql.repository.SpringSecurityUserEntityRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class SpringSecurityUserService {

    /**
     * 1. This is a good candidate for "constructor" injection.
     * 2. The member must have `final` modifier.
     * 3. Property injection is used for learning purpose only.
     */

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    // @Autowired
    private final SpringSecurityUserEntityRepository springSecurityUserRepository;

    // @Autowired
    private final SpringSecurityAuthorityEntityRepository springSecurityAuthorityEntityRepository;

    // @Autowired
    private final CountryRepository countryRepository;

    @Transactional
    public SpringSecurityUserEntity save(SheinUserDto userDto)
        throws AppException {
        try {
            SpringSecurityUserEntity ssuser = new SpringSecurityUserEntity();

            ssuser.setUsername(userDto.getEmail());
            ssuser.setPassword(userDto.getPassword());
            ssuser.setLastname(userDto.getLastname());
            ssuser.setFirstname(userDto.getFirstname());
            ssuser.setBusinessName(userDto.getBusinessname());
            ssuser.setIsBuyOnly(userDto.getIsBuyOnly());
            ssuser.setPhone(userDto.getPhone());

            if (userDto.getCountryCode() != null) {
                String countryCode = userDto.getCountryCode();
                Country country = countryRepository.findByCode(countryCode);
                ssuser.setCountry(country);
            }

            ssuser = springSecurityUserRepository.save(ssuser);

            SpringSecurityAuthorityEntity admin = new SpringSecurityAuthorityEntity();
            admin.setAuthority(SpringSecurityAuthorityEntityEnum.Admin.toString());
            admin.setUsername(userDto.getUsername());
            admin.setUser(ssuser);
            ssuser.getAuthorities().add(admin);

            springSecurityAuthorityEntityRepository.save(admin);

            SpringSecurityAuthorityEntity user = new SpringSecurityAuthorityEntity();
            user.setAuthority(SpringSecurityAuthorityEntityEnum.User.toString());
            user.setUsername(userDto.getUsername());
            user.setUser(ssuser);
            ssuser.getAuthorities().add(user);

            springSecurityAuthorityEntityRepository.save(user);

            return ssuser;
        } catch (Exception e) {
            throw new AppException("springSecurityUserDao.save(SpringSecurityUserEntity) Exception", e);
        }
    }

    public SpringSecurityUserEntity findByUsername(String username)
        throws AppException {
        try {
            return springSecurityUserRepository.findByUsername(username);
        } catch (Exception e) {
            throw new AppException("springSecurityUserDao.save(SpringSecurityUserEntity) Exception", e);
        }
    }

    public SpringSecurityUserEntity findByPhone(String phone)
        throws AppException {
        try {
            return springSecurityUserRepository.findByPhone(phone);
        } catch (Exception e) {
            throw new AppException("springSecurityUserDao.save(SpringSecurityUserEntity) Exception", e);
        }
    }

}
