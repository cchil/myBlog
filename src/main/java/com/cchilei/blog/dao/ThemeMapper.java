package com.cchilei.blog.dao;

import com.cchilei.blog.pojo.Theme;

public interface ThemeMapper {

    Theme select();

    int update(Theme theme);
}