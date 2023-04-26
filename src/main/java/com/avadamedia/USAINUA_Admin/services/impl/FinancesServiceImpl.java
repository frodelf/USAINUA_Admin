package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Finances;
import com.avadamedia.USAINUA_Admin.repositories.FinancesRepository;
import com.avadamedia.USAINUA_Admin.services.FinancesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinancesServiceImpl implements FinancesService {
    private final FinancesRepository financesRepository;

    public void save(Finances finances){financesRepository.save(finances);}
}
