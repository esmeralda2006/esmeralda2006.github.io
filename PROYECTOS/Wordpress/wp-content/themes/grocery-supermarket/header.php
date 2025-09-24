<?php
/**
 * The header for our theme
 *
 * This is the template that displays all of the <head> section and everything up until <div id="content">
 *
 * @link https://developer.wordpress.org/themes/basics/template-files/#template-partials
 *
 * @package Grocery Supermarket
 */

?>
<!doctype html>
<html <?php language_attributes(); ?>>
<head>
    <meta charset="<?php bloginfo('charset'); ?>">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <?php wp_head(); ?>
</head>

<body <?php body_class(); ?>>

<?php if ( function_exists( 'wp_body_open' ) ) { wp_body_open();} else { do_action( 'wp_body_open' ); } ?>

<?php if(get_theme_mod('grocery_supermarket_preloader_hide','')){ ?>
    <div class="loading">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
    </div>
<?php } ?>
<div id="page" class="site">
    <div class="<?php if(get_theme_mod('grocery_supermarket_site_width_layout','Full Width') == 'Wide Width'){?>container-fluid<?php } elseif(get_theme_mod('grocery_supermarket_site_width_layout','Full Width') == 'Container Width') {?>container<?php }?>">
        <a class="skip-link screen-reader-text" href="#skip-content"><?php esc_html_e('Skip to content', 'grocery-supermarket'); ?></a>
        <header id="masthead" class="site-header shadow-sm navbar-dark bg-primary">
            <?php get_template_part('template-parts/topheader/topheader'); ?>
            <div class="socialmedia">
                <?php get_template_part('template-parts/topheader/topheader1'); ?>
                <?php get_template_part('template-parts/topheader/mainheader'); ?>
            </div>
        </header>