<?php
/**
 * Displays main header
 *
 * @package Grocery Supermarket
 */
?>

<div class="main-header text-center text-md-start px-5">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-2 col-md-3 col-sm-6 col-6 align-self-center">
                <div class="all-categories">
                    <?php if(class_exists('woocommerce')){ ?>
                        <button class="cat-btn"><?php esc_html_e('All Categories','grocery-supermarket'); ?> <i class="fas fa-chevron-down"></i></button>
                        <div class="home_product_cat">
                          <?php $grocery_supermarket_args = array(
                              'number'     => '',
                              'orderby'    => 'title',
                              'order'      => 'ASC',
                              'hide_empty' => '',
                              'include'    => ''
                          );
                          $grocery_supermarket_product_categories = get_terms( 'product_cat', $grocery_supermarket_args );
                          $grocery_supermarket_count = count($grocery_supermarket_product_categories);
                            if ( $grocery_supermarket_count > 0 ){
                              foreach ( $grocery_supermarket_product_categories as $product_category ) {
                              echo '<h4><a href="' . get_term_link( $product_category ) . '">' . $product_category->name . '</a></h4>';
                              $grocery_supermarket_args = array(
                                'posts_per_page' => -1,
                                'tax_query' => array(
                                  'relation' => 'AND',
                                  array(
                                    'taxonomy' => 'product_cat',
                                    'field' => 'slug',
                                    'terms' => $product_category->slug
                                  )
                                ),
                                'post_type' => 'product',
                                'orderby' => 'title,'
                              );
                            }
                          }?>
                        </div>
                    <?php }?>
                </div>
            </div>
            <div class="col-lg-2 col-md-3 col-sm-6 col-6 align-self-center">
              <?php if ( get_theme_mod('grocery_supermarket_header_deals_url') != "" || get_theme_mod('grocery_supermarket_header_deals_button') != ""  ) {?>
                <a class="deals-btn" href="<?php echo esc_url(get_theme_mod('grocery_supermarket_header_deals_url')); ?>"><i class="fas fa-fire-alt"></i><?php echo esc_html(get_theme_mod('grocery_supermarket_header_deals_button')); ?></a>
              <?php }?>
            </div>
            <div class="col-lg-6 col-md-3 col-sm-4 col-4 align-self-center">
                <?php get_template_part('template-parts/navigation/nav'); ?>
            </div>
            <div class="col-lg-2 col-md-3 col-sm-8 col-8 align-self-center text-center text-md-end">
                <div class="header-phone">  
                    <?php if ( get_theme_mod('grocery_supermarket_header_phone_number') != "" ) {?>
                        <i class="fab fa-whatsapp"></i><a href="tel:<?php echo esc_attr(get_theme_mod('grocery_supermarket_header_phone_number')); ?>"><?php echo esc_html(get_theme_mod('grocery_supermarket_header_phone_number')); ?></a>
                    <?php }?>
                </div>
            </div>
        </div>
    </div>
</div>
