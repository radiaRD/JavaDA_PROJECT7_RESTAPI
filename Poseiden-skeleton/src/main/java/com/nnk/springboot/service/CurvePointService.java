package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import javax.validation.Valid;

@Service
public class CurvePointService {
    @Autowired
    CurvePointRepository curvePointRepository;

    public String home(Model model) {
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "curvePoint/list";
    }

    public void validate(@Valid CurvePoint curvePoint, Model model) {
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());

    }

    public void showUpdateForm(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve Point Id:" + id));
        curvePoint.setCurveId(0);
        curvePoint.setTerm(0.0);
        curvePoint.setValue(0.0);
        model.addAttribute("curvePoint", curvePoint);
    }

    public void updateBid(Integer id, @Valid CurvePoint curvePoint,
                          Model model) {
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());

    }

    public void deleteBid(Integer id, Model model) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve Point Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
    }

}
