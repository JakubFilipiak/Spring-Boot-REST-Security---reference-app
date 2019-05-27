package com.jakubfilipiak.restthymeleaf.commons;

/**
 * Created by Jakub Filipiak on 08.05.2019.
 */
public interface Mapper<F, T> {

    T map(F dao);

    F reverseMap(T dto);
}
