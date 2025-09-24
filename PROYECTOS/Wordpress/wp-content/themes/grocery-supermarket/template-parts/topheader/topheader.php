<?php
/**
 * Displays top header
 *
 * @package Grocery Supermarket
 */
?>

<div class="top-info text-end px-5 py-2">
	<div class="container-fluid">
        <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-12 col-12 align-self-center btn-box text-start">
                <?php if ( get_theme_mod('grocery_supermarket_topbar_about_button') != "" || get_theme_mod('grocery_supermarket_topbar_about_url') != ""  ) {?>
                   <span ><a href="<?php echo esc_url(get_theme_mod('grocery_supermarket_topbar_about_url')); ?>"><?php echo esc_html(get_theme_mod('grocery_supermarket_topbar_about_button')); ?></a></span>
                <?php }?>
                <?php if ( get_theme_mod('grocery_supermarket_topbar_wishlist_button') != "" || get_theme_mod('grocery_supermarket_topbar_wishlist_url') != ""  ) {?>
                <span ><a href="<?php echo esc_url(get_theme_mod('grocery_supermarket_topbar_wishlist_url')); ?>"><?php echo esc_html(get_theme_mod('grocery_supermarket_topbar_wishlist_button')); ?></a></span>
                <?php }?>
                <?php if ( get_theme_mod('grocery_supermarket_topbar_order_button') != "" || get_theme_mod('grocery_supermarket_topbar_order_url') != ""  ) {?>
                <span ><a href="<?php echo esc_url(get_theme_mod('grocery_supermarket_topbar_order_url')); ?>"><?php echo esc_html(get_theme_mod('grocery_supermarket_topbar_order_button')); ?></a></span>
                <?php }?>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-8 col-12 center-text align-self-center">
                <?php if ( get_theme_mod('grocery_supermarket_topbar_text') != "" ) {?>
                    <h4 class="m-0"><?php echo esc_html(get_theme_mod('grocery_supermarket_topbar_text')); ?></h4>
                <?php }?>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-4 col-12 align-self-center right-box">
                <div class="text-center translate-btn">
                    <?php if(class_exists('GTranslate')){ ?>
                        <?php echo do_shortcode('[gtranslate]', 'grocery-supermarket');?>
                    <?php }?>
                </div>
            </div>
        </div>
	</div>
</div>