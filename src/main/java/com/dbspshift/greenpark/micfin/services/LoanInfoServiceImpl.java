package com.dbspshift.greenpark.micfin.services;

import com.dbspshift.greenpark.micfin.Others.LoanCalculationsManager;
import com.dbspshift.greenpark.micfin.beans.LoanInfo;
import com.dbspshift.greenpark.micfin.beans.LoanSchedule;
import com.dbspshift.greenpark.micfin.exceptions.LoanInfoNotFoundException;
import com.dbspshift.greenpark.micfin.repository.LoanInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.dbspshift.greenpark.micfin.Others.MicFinPropererties.LOAN_ID_BEG;

@Service
public class LoanInfoServiceImpl implements LoanInfoService{

    @Autowired
    LoanInfoRepository loanInfoRepository;
    @Autowired
    LoanCalculationsManager loanCalculationsManager;

    /*@Override
    public LoanInfo registerLoanInfo(LoanInfo loanInfo) throws Exception {
        Optional<LoanInfo> byId = loanInfoRepository.findByLoanId(loanInfo.getLoanId());
        if(byId.isPresent()){
            throw new LoanInfoNotFoundException("LoanInfo is already present: Duplicate entry - [ID = "+loanInfo.getLoanId()+"  ]");
        }
        else {
            //loanInfo.setLoanId(loanInfo.getLoanId().toUpperCase());
            double emi = loanCalculationsManager.getEmi(loanInfo.getLoanAmount(), loanInfo.getInterestRate(), loanInfo.getTenure());
            loanCalculationsManager.getLoanSchedule(emi, loanInfo.getLoanAmount(), loanInfo.getInterestRate(), loanInfo.getListLoanSchedule());
            loanInfo.setEmi(emi);
            double i = loanInfo.getLoanAmount();
            loanInfo.setLoanBalance(i);
            return loanInfoRepository.insert(loanInfo);
        }
    }*/

    @Override
    public LoanInfo registerLoanInfo(LoanInfo loanInfo) throws Exception {

        Optional<Integer> byMaxLoanId = loanInfoRepository.getMaxLoanId();
        String newLoanId = "";
        if(byMaxLoanId.isPresent()){
            Integer integer = byMaxLoanId.get();
            newLoanId = LOAN_ID_BEG + String.valueOf(integer + 1);
        }
        else{
            newLoanId = LOAN_ID_BEG + "1";
        }
        loanInfo.setLoanId(newLoanId);
        double emi = loanCalculationsManager.getEmi(loanInfo.getLoanAmount(), loanInfo.getInterestRate(), loanInfo.getTenure());
        //loanCalculationsManager.getLoanSchedule(emi, loanInfo.getLoanAmount(), loanInfo.getInterestRate(), loanInfo.getListLoanSchedule());
        loanCalculationsManager.getLoanSchedule(emi, loanInfo.getLoanAmount(), loanInfo.getInterestRate(),loanInfo.getDate(), loanInfo.getListLoanSchedule());
        loanInfo.setEmi(emi);
        double i = loanInfo.getLoanAmount();
        loanInfo.setLoanBalance(i);
        return loanInfoRepository.insert(loanInfo);
        //}
    }

    @Override
    public LoanInfo getLoanInfoByLoanId(String loanId) throws Exception {
        //Optional<LoanInfo> byId = loanInfoRepository.findAll().stream().filter(li -> li.getLoanId().equals(loanId.trim())).findFirst();
        Optional<LoanInfo> byId = loanInfoRepository.findByLoanId(loanId);
        if(byId.isPresent())
            return byId.get();
        else
            throw new LoanInfoNotFoundException("Could not find details for LoanInfo - [ID = "+loanId+"  ]");
    }

    @Override
    public List<LoanInfo> getAllLoanInfos() throws Exception {
        return loanInfoRepository.findAll();
    }

    @Override
    public List<LoanInfo> getAllLoanInfosForMFI(String mfiId) throws Exception {
        /*Predicate<LoanInfo> predFilterByMeId = rp -> rp.getMfiId().equals(mfiId);
        List<LoanInfo> collect = loanInfoRepository.findAll().stream().filter(p -> predFilterByMeId.test(p)).collect(Collectors.toList());
        return collect;*/
        Optional<List<LoanInfo>> byMfiId = loanInfoRepository.findByMfiId(mfiId);
        if(byMfiId.isPresent())
            return byMfiId.get();
        else
            throw new LoanInfoNotFoundException("Could not find loan details for MFI- [ID = "+mfiId+"  ]");
    }

    @Override
    public List<LoanSchedule> getLoanScheduleForLoanId(String loanId) throws LoanInfoNotFoundException {
        Optional<List<LoanSchedule>> loanSchedule = loanInfoRepository.getLoanSchedule(loanId);
        if(loanSchedule.isPresent()){
            return loanSchedule.get();
        }
        else{
            throw new LoanInfoNotFoundException("Could not find loan schedule for loanId- [ID = "+loanId+"  ]");
        }
    }

    @Override
    public List<LoanInfo> getAllLoanInfosForMfiAndMicroEntrepreneur(String mfiId, String microEntId) throws Exception{
        Optional<List<LoanInfo>> byMfiIdMEId = loanInfoRepository.findByMfiIdMicroEntrepreneurId(mfiId,microEntId);
        if(byMfiIdMEId.isPresent())
            return byMfiIdMEId.get();
        else
            throw new LoanInfoNotFoundException("Could not find loan details for MFI- [ID = "+mfiId+"  ] and ME- [ID = \"+microEntId+\"  ]");
    }

    @Override
    public LoanInfo updateLoanInfo(LoanInfo loanInfo) throws Exception {
        Optional<LoanInfo> byId = loanInfoRepository.findByLoanId(loanInfo.getLoanId());
        if(byId.isPresent()) {
            return loanInfoRepository.save(loanInfo);
        }
        else
            throw new LoanInfoNotFoundException("Could not update LoanInfo - [ID = "+loanInfo.getLoanId()+"  ]");
    }

    @Override
    public String deleteLoanInfo(String id) throws Exception {
        try {
            loanInfoRepository.deleteById(id);
            return "success";
        }catch(Exception e){
            return "failed";
        }
    }
}

//Optional<LoanInfo> byId = loanInfoRepository.findByLoanId(loanInfo.getLoanId());
//if(byId.isPresent()){
//throw new LoanInfoNotFoundException("LoanInfo is already present: Duplicate entry - [ID = "+loanInfo.getLoanId()+"  ]");
//}
//else {
//loanInfo.setLoanId(loanInfo.getLoanId().toUpperCase());
