package com.eju.cy.uploadcardlibrary.callback;


/**
* @ Name: Caochen
* @ Date: 2019-08-23
* @ Time: 11:30
* @ Description： 被观察者
*/
public interface EjuHomeObservable {


    void addObserver(EjuHomeObserver observer);


    void removeObserver(EjuHomeObserver observer);


    void notifyObservers(Object obj);
}
