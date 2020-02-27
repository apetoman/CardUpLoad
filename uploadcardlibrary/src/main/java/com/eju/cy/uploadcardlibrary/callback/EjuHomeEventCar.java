package com.eju.cy.uploadcardlibrary.callback;

/**
* @ Name: Caochen
* @ Date: 2019-08-23
* @ Time: 13:45
* @ Description： 单例工具类
*/
public class EjuHomeEventCar {

    private static EjuHomeEventCar instance;
    private ConcreteObservableStatistics observableA;

    public static EjuHomeEventCar getDefault() {
        if (instance == null) {
            synchronized (EjuHomeEventCar.class) {
                if (instance == null) {
                    instance = new EjuHomeEventCar();
                }
            }
        }
        return instance;
    }

    private EjuHomeEventCar() {
        observableA = new ConcreteObservableStatistics();
    }


    public void register(EjuHomeObserver observer) {
        observableA.addObserver(observer);
    }


    public void unregister(EjuHomeObserver observer) {
        observableA.removeObserver(observer);
    }


    public void post(Object obj) {
        observableA.notifyObservers(obj);
    }
}
