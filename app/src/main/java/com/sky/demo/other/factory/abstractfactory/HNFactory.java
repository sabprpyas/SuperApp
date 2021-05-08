package com.sky.demo.other.factory.abstractfactory;

/**
 * 新年系列加工厂
 *
 * @author Administrator
 */
public class HNFactory implements PersonFactory {

    @Override
    public Boy getBoy() {
        return new HNBoy();
    }

    @Override
    public Girl getGirl() {
        return new HNGirl();
    }

}

