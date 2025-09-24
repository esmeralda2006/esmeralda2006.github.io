<?php
/**
 * Displays top header 1
 *
 * @package Grocery Supermarket
 */
?>

<div class="top-header px-5 py-2">
	<div class="container-fluid">
        <div class="row">
            <div class="col-lg-3 col-md-4 col-sm-6 col-12 align-self-center">
                <div class="navbar-brand text-center text-md-start">
                    <?php if ( has_custom_logo() ) : ?>
                        <div class="site-logo"><?php the_custom_logo(); ?></div>
                    <?php endif; ?>
                    <?php $grocery_supermarket_blog_info = get_bloginfo( 'name' ); ?>
                        <?php if ( ! empty( $grocery_supermarket_blog_info ) ) : ?>
                            <?php if ( is_front_page() && is_home() ) : ?>
                                <?php if( get_theme_mod('grocery_supermarket_logo_title_text',true) != ''){ ?>
                                    <h1 class="site-title"><a href="<?php echo esc_url( home_url( '/' ) ); ?>" rel="home"><?php bloginfo( 'name' ); ?></a></h1>
                                <?php } ?>
                            <?php else : ?>
                                <?php if( get_theme_mod('grocery_supermarket_logo_title_text',true) != ''){ ?>
                                    <p class="site-title"><a href="<?php echo esc_url( home_url( '/' ) ); ?>" rel="home"><?php bloginfo( 'name' ); ?></a></p>
                                <?php } ?>
                            <?php endif; ?>
                        <?php endif; ?>
                        <?php
                            $grocery_supermarket_description = get_bloginfo( 'description', 'display' );
                            if ( $grocery_supermarket_description || is_customize_preview() ) :
                        ?>
                        <?php if( get_theme_mod('grocery_supermarket_theme_description',false) != ''){ ?>
                            <p class="site-description"><?php echo esc_html($grocery_supermarket_description); ?></p>
                        <?php } ?>
                    <?php endif; ?>
                </div>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-6 col-12 align-self-center product-search text-end">
                <?php if(class_exists('woocommerce')){ ?>
                    <?php get_product_search_form(); ?>
                <?php }?>
            </div>
            <div class="col-lg-5 col-md-5 col-sm-12 col-12 btn-box align-self-center text-end">
                <?php if ( get_theme_mod('grocery_supermarket_topbar1_wishlist_url') != "" || get_theme_mod('grocery_supermarket_topbar1_wishlist_button') != ""  ) {?>
                    <span class="wish-btn">
                        <a href="<?php echo esc_url(get_theme_mod('grocery_supermarket_topbar1_wishlist_url')); ?>"><i class="far fa-heart"></i><?php echo esc_html(get_theme_mod('grocery_supermarket_topbar1_wishlist_button')); ?></a>
                    </span>
                <?php }?>
                <span class="cart_no">
                    <?php if(class_exists('woocommerce')){ ?>
                        <?php global $woocommerce; ?>
                        <a class="cart-customlocation" href="<?php echo esc_url(wc_get_cart_url()); ?>" title="<?php esc_attr_e( 'shopping cart','grocery-supermarket' ); ?>"><i class="fas fa-cart-plus"></i><span class="cart-value"><?php echo sprintf ( esc_html( '%d', '%d', WC()->cart->get_cart_contents_count() ), WC()->cart->get_cart_contents_count() ); ?></span><span class="cart-text"><?php esc_html_e('Cart','grocery-supermarket'); ?></span></a>
                    <?php }?>
                </span>
                <?php if(class_exists('woocommerce')){ ?>
                    <span class="user-btn">
                        <?php if ( is_user_logged_in() ) { ?>
                            
                            <a class="account-btn" href="<?php echo esc_url( get_permalink( get_option('woocommerce_myaccount_page_id') ) ); ?>" title="<?php esc_attr_e('Account','grocery-supermarket'); ?>"><i class="fas fa-user"></i><?php esc_html_e('Account','grocery-supermarket'); ?></a>
                        <?php } 
                        else { ?>
                            
                            <a class="account-btn" href="<?php echo esc_url( get_permalink( get_option('woocommerce_myaccount_page_id') ) ); ?>" title="<?php esc_attr_e('Account','grocery-supermarket'); ?>"><i class="fas fa-user"></i><?php esc_html_e('Account','grocery-supermarket'); ?></a>
                        <?php } ?>
                    </span>
                <?php }?>
            </div>
        </div>
	</div>
</div>
<hr>