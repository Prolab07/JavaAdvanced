package com.advanced.spring.core.beans;

import com.advanced.spring.core.di.Car;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/*
"Бін" (bean) у контексті Spring Framework - це об'єкт, який управляється та створюється контейнером Spring.
Він також може мати життєвий цикл, який контролюється Spring.
 Поняття "бін" походить від "Компонентних об'єктів" (Component Object), які можна розглядати як частини програми.

Ось декілька ключових аспектів бінів у Spring:

Конфігурація: Біни можуть бути конфігуровані XML-файлами або анотаціями
(наприклад, @Component, @Service, @Repository, @Controller тощо).

Життєвий цикл: Spring надає можливість втручатися в різні етапи життєвого циклу біна,
включаючи його ініціалізацію та знищення.

Область видимості: Біни можуть мати різні області видимості,
такі як "singleton" (один екземпляр на весь контекст), "prototype" (новий екземпляр кожного разу), та інші.

Залежності: Spring контролює ін'єкцію залежностей між бінами.
 Це може бути реалізовано через конструктор, методи сеттера або поля.

Лінива ініціалізація: За замовчуванням усі "singleton" біни ініціалізуються при створенні контексту.
 Але іноді може бути корисним відкладати цей процес до того моменту,
  коли бін дійсно буде потрібен. Це називається "лінивою ініціалізацією".

Інтерцептори та "поради" (advices): За допомогою AOP (аспектно-орієнтоване програмування)
 Spring дозволяє "перехоплювати" виклики до бінів та додавати додаткову логіку перед,
 після або навіть замість самого виклику.

Коли ми говоримо про Spring, термін "бін" часто використовується для посилання на об'єкти,
 які створює та управляє контейнером Spring.
 */
@Service
@Log4j2
public class ServiceBean implements ApplicationContextAware, InitializingBean, DisposableBean {

    private ApplicationContext context; // not recommended

    @Autowired
    @Qualifier("cat")
    private Animal animal;

    @Autowired
    private Car autowireFielddCar; // not recommended

    private Car autowiredSetterInjectedCar;

    private final Car autowiredConstructorCar; // good one

    @Autowired
    public ServiceBean(Car autowiredConstructorCar) {
        this.autowiredConstructorCar = autowiredConstructorCar;
    }

    @Autowired
    public void setAutowiredSetterInjectedCar(Car autowiredSetterInjectedCar) {
        this.autowiredSetterInjectedCar = autowiredSetterInjectedCar;
    }

    /*
    ця анотація вказує на метод, який слід викликати після того, як бін буде ініціалізований.
     Цей метод викликається перед тим, як бін буде використаний.
     */
    @PostConstruct
    public void getBeans() {
        log.info("get autowiredCar");
        autowireFielddCar.start();

        log.info("get autowiredConstructorCar");
        autowiredConstructorCar.start();

        log.info("get autowiredSetterInjectedCar");
        autowiredSetterInjectedCar.start();

        log.info("get bean from context");
        Car car = context.getBean(Car.class);
        car.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        log.info("InitializingBean (afterPropertiesSet) in ServiceBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DisposableBean (destroy) in ServiceBean");
    }
}
