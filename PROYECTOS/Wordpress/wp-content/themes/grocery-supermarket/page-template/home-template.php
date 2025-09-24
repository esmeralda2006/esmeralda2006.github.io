<?php
/**
 * Template Name: Home Template
 */

get_header(); ?>

<main id="skip-content">
  <?php if(get_theme_mod('grocery_supermarket_top_slider_setting',1) == 1){ ?>
    <section id="top-slider" >
      <?php $grocery_supermarket_slide_pages = array();
        for ( $grocery_supermarket_count = 1; $grocery_supermarket_count <= 3; $grocery_supermarket_count++ ) {
          $grocery_supermarket_mod = intval( get_theme_mod( 'grocery_supermarket_top_slider_page' . $grocery_supermarket_count ));
          if ( 'page-none-selected' != $grocery_supermarket_mod ) {
            $grocery_supermarket_slide_pages[] = $grocery_supermarket_mod;
          }
        }
        if( !empty($grocery_supermarket_slide_pages) ) :
          $grocery_supermarket_args = array(
            'post_type' => 'page',
            'post__in' => $grocery_supermarket_slide_pages,
            'orderby' => 'post__in'
          );
          $grocery_supermarket_query = new WP_Query( $grocery_supermarket_args );
          if ( $grocery_supermarket_query->have_posts() ) :
            $i = 1;
      ?>
      <div class="owl-carousel" role="listbox">
        <?php  while ( $grocery_supermarket_query->have_posts() ) : $grocery_supermarket_query->the_post(); ?>
          <div class="slider-box">
            <?php if(has_post_thumbnail()){
              the_post_thumbnail();
              } else{?>
              <img src="<?php echo esc_url(get_template_directory_uri()); ?>/assets/image/slider.png" alt="" />
            <?php } ?>
            <div class="slider-inner-box">
              <?php if(get_theme_mod('grocery_supermarket_slider_title_setting',1) == 1){ ?>
                <h2><?php the_title(); ?></h2>
              <?php }?>
              <?php if(get_theme_mod('grocery_supermarket_slider_content_setting',1) == 1){ ?>
                <p><?php echo esc_html( wp_trim_words( get_the_content(), esc_attr(get_theme_mod('grocery_supermarket_slider_excerpt_length', 15)) )); ?></p>
              <?php }?>
              <?php if(get_theme_mod('grocery_supermarket_slider_button_setting',1) == 1 && get_theme_mod('grocery_supermarket_slider_button_text','VIEW MORE') != ''){ ?>
                <div class="slide-btn mt-4"><a href="<?php the_permalink(); ?>"><?php esc_html_e(get_theme_mod('grocery_supermarket_slider_button_text','VIEW MORE')); ?></a></div>
              <?php }?>
            </div>
          </div>
        <?php $i++; endwhile;
        wp_reset_postdata();?>
      </div>
      <?php else : ?>
        <div class="no-postfound"></div>
      <?php endif;
      endif;?>
    </section>
  <?php }?>

  <section id="best-sell" class=" py-5 px-5">
    <div class="container-fluid">
      <div class="heading  text-center">
        <?php if ( get_theme_mod('grocery_supermarket_best_sells_section_heading') != "" ) {?>
          <h3 class="main_heading text-center m-0"><?php echo esc_html(get_theme_mod('grocery_supermarket_best_sells_section_heading')); ?>
          </h3>
        <?php }?>
        <?php if ( get_theme_mod('grocery_supermarket_best_sells_section_sub_heading') != "" ) {?>
          <h4 class=" text-center "><?php echo esc_html(get_theme_mod('grocery_supermarket_best_sells_section_sub_heading')); ?>
          </h4>
        <?php }?>
      </div>
        <div class="row media-row">
      <div class="col-md-12" id="content-box">
       <div class="prod_wrapper" id="featuredproduct">
          <div class="heading row mb-3 mt-2">
             <div class="col-md-12 col-sm-12 men-tabs">
                <ul class="nav-tabs nav justify-content-center" role="tablist">
                   <?php $tab_count = get_theme_mod('grocery_supermarket_tab_number', 4); 
                      for($i=1; $i<= $tab_count; $i++ ) {?>
                      <?php if ( get_theme_mod('grocery_supermarket_slideproduct_tab1title'.$i) != "" ) {?>
                         <li class="nav-item">
                            <a class="nav-link <?php if($i == 1){echo 'active';} ?>" href="#tab<?php echo esc_attr($i);?>" role="tab" data-bs-toggle="tab"><?php echo esc_html(get_theme_mod('grocery_supermarket_slideproduct_tab1title'.$i)); ?></a>
                         </li>
                      <?php }?>
                   <?php }?>
                </ul>
             </div>
          </div>
          <div class="container-fluid">
            <div id="mens-product-wrap">
              <div class="tab-content">
                <!--tab 1 -->
                <?php $tab_count = get_theme_mod('grocery_supermarket_tab_number' ,4); 
                   for($i=1; $i<= $tab_count; $i++ ) {?>
                <div role="tabpanel" class="tab-pane <?php if($i == 1){echo 'active';} ?>" id   ="tab<?php echo esc_attr($i);?>">
                  <?php if(class_exists('woocommerce')){ ?>
                   <div class="owl-carousel">
                      <?php 
                         $args = array(
                          'post_type' => 'product',
                          'product_cat' =>  get_theme_mod('grocery_supermarket_cate_tab'.$i),
                          'orderby' =>'date','order' => 'DESC' );
                         $loop = new WP_Query( $args );           
                         while ( $loop->have_posts() ){
                         $loop->the_post(); 
                         global $product; ?>
                        <div class="sells-product">
                               <div class="mask1">
                                 <div class="prodimg_box">
                                  <a href="<?php echo esc_url(get_permalink( $loop->post->ID )); ?>" title="<?php echo esc_attr($loop->post->post_title ? $loop->post->post_title : $loop->post->ID); ?>">
                                    <?php if (has_post_thumbnail( $loop->post->ID )) echo get_the_post_thumbnail($loop->post->ID, 'our_product'); else echo '<img src="'.esc_url(wc_placeholder_img_src()).'" alt="Placeholder" width="300px" height="300px" />'; ?>
                                  </a>
                                 </div>
                                 <div class="text_box">
                                    <span class="woo-cat"><?php echo wc_get_product_category_list( $product->get_id(),); ?></span>
                                    <h4 class="hidedesktop p-0 mb-2"><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h4>
                                    <div class="row mb-2">
                                      <div class="col-lg-6 col-md-6 col-sm-12 col-6 align-self-center">
                                        <p class="<?php echo esc_attr( apply_filters( 'woocommerce_product_price_class', 'price' ) ); ?> mb-0"><?php echo $product->get_price_html(); ?></p>
                                      </div>
                                      <div class="col-lg-6 col-md-6 col-sm-12 col-6 align-self-center">
                                        <?php if( $product->is_type( 'simple' ) ){ woocommerce_template_loop_rating( $loop->post, $product ); } ?>
                                      </div>
                                    </div>
                                    <span class="sale_cart">
                                      <?php if( $product->is_type( 'simple' ) ){ woocommerce_template_loop_add_to_cart( $loop->post, $product ); } ?>
                                    </span>
                                </div>
                              </div>
                            
                        </div>
                      <?php  } wp_reset_query(); ?>
                   </div>
                  <?php }?>
                </div>
                <?php }?>
              </div>
            </div>
          </div>
        </div>
      </div>
        </div>
    </div>
  </section>
  <section id="page-content">
    <div class="container">
      <div class="py-5">
        <?php
          if ( have_posts() ) :
            while ( have_posts() ) : the_post();
              the_content();
            endwhile;
          endif;
        ?>
      </div>
    </div>
  </section>
</main>

<?php get_footer(); ?>