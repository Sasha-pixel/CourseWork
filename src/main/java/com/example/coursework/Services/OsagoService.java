package com.example.coursework.Services;

import com.example.coursework.Data.Entities.Contract;
import com.example.coursework.Data.Entities.Osago;
import com.example.coursework.Data.Repositories.OsagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OsagoService {

    @Autowired
    private ContractService contractService;

    @Autowired
    private OsagoRepository osagoRepository;

    public void approveContract(Long id) {
        Contract contract = contractService.findById(id);
        contractService.approveContract(contract);
        if (contract != null) {
            Date startDate = new Date();
            Date endDate = new Date(startDate.getYear() + 1, startDate.getMonth(), startDate.getDay());
            Osago osago = new Osago(UUID.randomUUID().toString(), startDate, endDate, contract.getCar(), contract);
            osagoRepository.save(osago);
        }
    }
}
