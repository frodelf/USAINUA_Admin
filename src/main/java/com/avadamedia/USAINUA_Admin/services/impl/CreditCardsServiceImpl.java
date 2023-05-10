package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.CreditCard;
import com.avadamedia.USAINUA_Admin.repositories.CreditCardsRepository;
import com.avadamedia.USAINUA_Admin.services.CreditCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardsServiceImpl implements CreditCardsService {
    private final CreditCardsRepository creditCardsRepository;
    public void save(CreditCard creditCard){creditCardsRepository.save(creditCard);}
}
