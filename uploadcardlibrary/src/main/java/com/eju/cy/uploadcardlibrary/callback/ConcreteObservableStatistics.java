package com.eju.cy.uploadcardlibrary.callback;


import java.util.ArrayList;

/**
 * @ Name: Caochen
 * @ Date: 2019-08-23
 * @ Time: 11:32
 * @ Description： 被观察者的实现
 */
public class ConcreteObservableStatistics implements EjuHomeObservable {
    private ArrayList<EjuHomeObserver> observers;

    @Override
    public void addObserver(EjuHomeObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(EjuHomeObserver observer) {
        if (observers == null || observers.size() <= 0) {
            return;
        }
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        if (observers == null || observers.size() <= 0) {
            return;
        }
        for (EjuHomeObserver observer : observers) {
            observer.update(obj);
        }
    }
}
