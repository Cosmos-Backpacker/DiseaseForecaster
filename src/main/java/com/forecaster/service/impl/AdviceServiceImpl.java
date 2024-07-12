package com.forecaster.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forecaster.mapper.AdviceMapper;
import com.forecaster.pojo.Advice;
import com.forecaster.service.IAdviceService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CosmosBackpacker
 * @since 2024-01-29
 */
@Service
public class AdviceServiceImpl extends ServiceImpl<AdviceMapper, Advice> implements IAdviceService {


    @Override
    public Advice getHeartByRandom() {
        Random random=new Random();

        String diet= getById((random.nextInt(10)+1)).getDiet();
        String motion=getById((random.nextInt(10)+1)).getMotion();
        String habit=getById((random.nextInt(10)+1)).getHabits();
        String psychology=getById((random.nextInt(10)+1)).getPsychology();

        Advice heartAdvice=new Advice(diet,motion,habit,psychology);

        return heartAdvice;
    }
}
