<?php

    $grocery_supermarket_theme_css= "";

    /*--------------------------- Scroll to top positions -------------------*/

    $grocery_supermarket_scroll_position = get_theme_mod( 'grocery_supermarket_scroll_top_position','Right');
    if($grocery_supermarket_scroll_position == 'Right'){
        $grocery_supermarket_theme_css .='#button{';
            $grocery_supermarket_theme_css .='right: 20px;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_scroll_position == 'Left'){
        $grocery_supermarket_theme_css .='#button{';
            $grocery_supermarket_theme_css .='left: 20px;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_scroll_position == 'Center'){
        $grocery_supermarket_theme_css .='#button{';
            $grocery_supermarket_theme_css .='right: 50%;left: 50%;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Scroll To Top Border Radius -------------------*/

    $grocery_supermarket_scroll_to_top_border_radius = get_theme_mod('grocery_supermarket_scroll_to_top_border_radius');
    $grocery_supermarket_scroll_bg_color = get_theme_mod('grocery_supermarket_scroll_bg_color');
    $grocery_supermarket_scroll_color = get_theme_mod('grocery_supermarket_scroll_color');
    $grocery_supermarket_scroll_font_size = get_theme_mod('grocery_supermarket_scroll_font_size');
    if($grocery_supermarket_scroll_to_top_border_radius != false || $grocery_supermarket_scroll_bg_color != false || $grocery_supermarket_scroll_color != false || $grocery_supermarket_scroll_font_size != false){
        $grocery_supermarket_theme_css .='#colophon a#button{';
            $grocery_supermarket_theme_css .='border-radius: '.esc_attr($grocery_supermarket_scroll_to_top_border_radius).'px; background-color: '.esc_attr($grocery_supermarket_scroll_bg_color).'; color: '.esc_attr($grocery_supermarket_scroll_color).' !important; font-size: '.esc_attr($grocery_supermarket_scroll_font_size).'px;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Slider Opacity -------------------*/

    $grocery_supermarket_theme_lay = get_theme_mod( 'grocery_supermarket_slider_opacity_color','0.5');
    if($grocery_supermarket_theme_lay == '0'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.1'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.1';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.2'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.2';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.3'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.3';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.4'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.4';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.5'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.5';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.6'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.6';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.7'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.7';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.8'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.8';
        $grocery_supermarket_theme_css .='}';
        }else if($grocery_supermarket_theme_lay == '0.9'){
        $grocery_supermarket_theme_css .='.slider-box img{';
            $grocery_supermarket_theme_css .='opacity:0.9';
        $grocery_supermarket_theme_css .='}';
        }

    /*---------------------------Slider Height ------------*/

    $grocery_supermarket_slider_img_height = get_theme_mod('grocery_supermarket_slider_img_height');
    if($grocery_supermarket_slider_img_height != false){
        $grocery_supermarket_theme_css .='#top-slider .owl-carousel .owl-item img{';
            $grocery_supermarket_theme_css .='height: '.esc_attr($grocery_supermarket_slider_img_height).';';
        $grocery_supermarket_theme_css .='}';
    }

    /*---------------- Single post Settings ------------------*/

    $grocery_supermarket_single_post_navigation_show_hide = get_theme_mod('grocery_supermarket_single_post_navigation_show_hide',true);
    if($grocery_supermarket_single_post_navigation_show_hide != true){
        $grocery_supermarket_theme_css .='.nav-links{';
            $grocery_supermarket_theme_css .='display: none;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Woocommerce Product Sale Positions -------------------*/

    $grocery_supermarket_product_sale = get_theme_mod( 'grocery_supermarket_woocommerce_product_sale','Right');
    if($grocery_supermarket_product_sale == 'Right'){
        $grocery_supermarket_theme_css .='.woocommerce ul.products li.product .onsale{';
            $grocery_supermarket_theme_css .='left: auto; right: 15px;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_product_sale == 'Left'){
        $grocery_supermarket_theme_css .='.woocommerce ul.products li.product .onsale{';
            $grocery_supermarket_theme_css .='left: 15px; right: auto;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_product_sale == 'Center'){
        $grocery_supermarket_theme_css .='.woocommerce ul.products li.product .onsale{';
            $grocery_supermarket_theme_css .='right: 50%;left: 50%;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Woocommerce Product Sale Border Radius -------------------*/

    $grocery_supermarket_woo_product_sale_border_radius = get_theme_mod('grocery_supermarket_woo_product_sale_border_radius');
    if($grocery_supermarket_woo_product_sale_border_radius != false){
        $grocery_supermarket_theme_css .='.woocommerce ul.products li.product .onsale{';
            $grocery_supermarket_theme_css .='border-radius: '.esc_attr($grocery_supermarket_woo_product_sale_border_radius).'px;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Woocommerce Product Border Radius -------------------*/

    $grocery_supermarket_woo_product_border_radius = get_theme_mod('grocery_supermarket_woo_product_border_radius', 0);
    if($grocery_supermarket_woo_product_border_radius != false){
        $grocery_supermarket_theme_css .='.woocommerce ul.products li.product a img{';
            $grocery_supermarket_theme_css .='border-radius: '.esc_attr($grocery_supermarket_woo_product_border_radius).'px;';
        $grocery_supermarket_theme_css .='}';
    }


    /*--------------------------- Footer background image -------------------*/

    $grocery_supermarket_footer_bg_image = get_theme_mod('grocery_supermarket_footer_bg_image');
    if($grocery_supermarket_footer_bg_image != false){
        $grocery_supermarket_theme_css .='#colophon{';
            $grocery_supermarket_theme_css .='background: url('.esc_attr($grocery_supermarket_footer_bg_image).')!important;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Copyright Background Color -------------------*/

    $grocery_supermarket_copyright_background_color = get_theme_mod('grocery_supermarket_copyright_background_color');
    if($grocery_supermarket_copyright_background_color != false){
        $grocery_supermarket_theme_css .='.footer_info{';
            $grocery_supermarket_theme_css .='background-color: '.esc_attr($grocery_supermarket_copyright_background_color).' !important;';
        $grocery_supermarket_theme_css .='}';
    } 

    /*--------------------------- Site Title And Tagline Color -------------------*/

    $grocery_supermarket_logo_title_color = get_theme_mod('grocery_supermarket_logo_title_color');
    if($grocery_supermarket_logo_title_color != false){
        $grocery_supermarket_theme_css .='p.site-title a, .navbar-brand a{';
            $grocery_supermarket_theme_css .='color: '.esc_attr($grocery_supermarket_logo_title_color).' !important;';
        $grocery_supermarket_theme_css .='}';
    }

    $grocery_supermarket_logo_tagline_color = get_theme_mod('grocery_supermarket_logo_tagline_color');
    if($grocery_supermarket_logo_tagline_color != false){
        $grocery_supermarket_theme_css .='.logo p.site-description, .navbar-brand p{';
            $grocery_supermarket_theme_css .='color: '.esc_attr($grocery_supermarket_logo_tagline_color).'  !important;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Footer Widget Content Alignment -------------------*/

    $grocery_supermarket_footer_widget_content_alignment = get_theme_mod( 'grocery_supermarket_footer_widget_content_alignment','Left');
    if($grocery_supermarket_footer_widget_content_alignment == 'Left'){
        $grocery_supermarket_theme_css .='#colophon ul, #colophon p, .tagcloud, .widget{';
        $grocery_supermarket_theme_css .='text-align: left;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_footer_widget_content_alignment == 'Center'){
        $grocery_supermarket_theme_css .='#colophon ul, #colophon p, .tagcloud, .widget{';
            $grocery_supermarket_theme_css .='text-align: center;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_footer_widget_content_alignment == 'Right'){
        $grocery_supermarket_theme_css .='#colophon ul, #colophon p, .tagcloud, .widget{';
            $grocery_supermarket_theme_css .='text-align: right;';
        $grocery_supermarket_theme_css .='}';
    }

    /*--------------------------- Copyright Content Alignment -------------------*/

    $grocery_supermarket_copyright_content_alignment = get_theme_mod( 'grocery_supermarket_copyright_content_alignment','Right');
    if($grocery_supermarket_copyright_content_alignment == 'Left'){
        $grocery_supermarket_theme_css .='.footer-menu-left{';
        $grocery_supermarket_theme_css .='text-align: left;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_copyright_content_alignment == 'Center'){
        $grocery_supermarket_theme_css .='.footer-menu-left{';
            $grocery_supermarket_theme_css .='text-align: center;';
        $grocery_supermarket_theme_css .='}';
    }else if($grocery_supermarket_copyright_content_alignment == 'Right'){
        $grocery_supermarket_theme_css .='.footer-menu-left{';
            $grocery_supermarket_theme_css .='text-align: right;';
        $grocery_supermarket_theme_css .='}';
    }

    /*------------------ Nav Menus -------------------*/

    $grocery_supermarket_nav_menu = get_theme_mod( 'grocery_supermarket_nav_menu_text_transform','Capitalize');
    if($grocery_supermarket_nav_menu == 'Capitalize'){
        $grocery_supermarket_theme_css .='.main-navigation .menu > li > a{';
            $grocery_supermarket_theme_css .='text-transform:Capitalize;';
        $grocery_supermarket_theme_css .='}';
    }
    if($grocery_supermarket_nav_menu == 'Lowercase'){
        $grocery_supermarket_theme_css .='.main-navigation .menu > li > a{';
            $grocery_supermarket_theme_css .='text-transform:Lowercase;';
        $grocery_supermarket_theme_css .='}';
    }
    if($grocery_supermarket_nav_menu == 'Uppercase'){
        $grocery_supermarket_theme_css .='.main-navigation .menu > li > a{';
            $grocery_supermarket_theme_css .='text-transform:Uppercase;';
        $grocery_supermarket_theme_css .='}';
    }

    $grocery_supermarket_menu_font_size = get_theme_mod( 'grocery_supermarket_menu_font_size');
    if($grocery_supermarket_menu_font_size != ''){
        $grocery_supermarket_theme_css .='.main-navigation .menu > li > a{';
            $grocery_supermarket_theme_css .='font-size: '.esc_attr($grocery_supermarket_menu_font_size).'px;';
        $grocery_supermarket_theme_css .='}';
    }

    $grocery_supermarket_nav_menu_font_weight = get_theme_mod( 'grocery_supermarket_nav_menu_font_weight',600);
    if($grocery_supermarket_menu_font_size != ''){
        $grocery_supermarket_theme_css .='.main-navigation .menu > li > a{';
            $grocery_supermarket_theme_css .='font-weight: '.esc_attr($grocery_supermarket_nav_menu_font_weight).';';
        $grocery_supermarket_theme_css .='}';
    }

    /*------------------ Slider CSS -------------------*/

    $grocery_supermarket_slider_content_layout = get_theme_mod( 'grocery_supermarket_slider_content_layout','Left');
    if($grocery_supermarket_slider_content_layout == 'Left'){
        $grocery_supermarket_theme_css .='.slider-inner-box, #top-slider .slider-inner-box p{';
            $grocery_supermarket_theme_css .='text-align : left;';
        $grocery_supermarket_theme_css .='}';
    }
    if($grocery_supermarket_slider_content_layout == 'Center'){
        $grocery_supermarket_theme_css .='.slider-inner-box, #top-slider .slider-inner-box p{';
            $grocery_supermarket_theme_css .='text-align : center;';
        $grocery_supermarket_theme_css .='}';
        $grocery_supermarket_theme_css .='.slider-inner-box{';
            $grocery_supermarket_theme_css .='right : 50%; left:10%';
        $grocery_supermarket_theme_css .='}';
    }
    if($grocery_supermarket_slider_content_layout == 'Right'){
        $grocery_supermarket_theme_css .='.slider-inner-box, #top-slider .slider-inner-box p{';
            $grocery_supermarket_theme_css .='text-align : right;';
        $grocery_supermarket_theme_css .='}';
        $grocery_supermarket_theme_css .='.slider-inner-box{';
            $grocery_supermarket_theme_css .='right : 50%; left:10%';
        $grocery_supermarket_theme_css .='}';
    }