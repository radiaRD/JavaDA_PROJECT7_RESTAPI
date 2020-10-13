package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import javax.validation.Valid;

/**
 * CurvePointService includes CRUD methods for curvePoint.
 */
@Service
public class CurvePointService {
    private static final Logger logger = LogManager.getLogger(CurvePointService.class);

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * find all curvePoint with curvePointRepository.findAll.
     *
     * @return the list of curvePoint.
     */
    public String home(Model model) {
        logger.info("Get curve points List");
        model.addAttribute("curvePoint", curvePointRepository.findAll());
        return "curvePoint/list";
    }

    /**
     * create a new curvePoint with curvePointRepository.save.
     */
    public void validate(@Valid CurvePoint curvePoint, Model model) {
        logger.info("Save a new curve point");
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());

    }

    /**
     * find the curvePoint by id with curvePointRepository.findById.
     */
    public void showUpdateForm(Integer id, Model model) {
        logger.info("Find a curve point with id :" + id);
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve Point Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
    }

    /**
     * find and update the curvePoint by id .
     */
    public void updateCurve(Integer id, @Valid CurvePoint curvePoint,
                            Model model) {
        logger.info("Update the curve point with id :" + id);
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());

    }

    /**
     * find and delete the curvePoint by id .
     */
    public void deleteCurve(Integer id, Model model) {
        logger.info("delete the curve point with id :" + id);
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve Point Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePoint", curvePointRepository.findAll());
    }

}
