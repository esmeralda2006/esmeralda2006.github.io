function grocery_supermarket_openNav() {
  jQuery(".sidenav").addClass('show');
}
function grocery_supermarket_closeNav() {
  jQuery(".sidenav").removeClass('show');
}

( function( window, document ) {
  function grocery_supermarket_keepFocusInMenu() {
    document.addEventListener( 'keydown', function( e ) {
      const grocery_supermarket_nav = document.querySelector( '.sidenav' );

      if ( ! grocery_supermarket_nav || ! grocery_supermarket_nav.classList.contains( 'show' ) ) {
        return;
      }
      const elements = [...grocery_supermarket_nav.querySelectorAll( 'input, a, button' )],
        grocery_supermarket_lastEl = elements[ elements.length - 1 ],
        grocery_supermarket_firstEl = elements[0],
        grocery_supermarket_activeEl = document.activeElement,
        tabKey = e.keyCode === 9,
        shiftKey = e.shiftKey;

      if ( ! shiftKey && tabKey && grocery_supermarket_lastEl === grocery_supermarket_activeEl ) {
        e.preventDefault();
        grocery_supermarket_firstEl.focus();
      }

      if ( shiftKey && tabKey && grocery_supermarket_firstEl === grocery_supermarket_activeEl ) {
        e.preventDefault();
        grocery_supermarket_lastEl.focus();
      }
    } );
  }
  grocery_supermarket_keepFocusInMenu();
} )( window, document );

var btn = jQuery('#button');

jQuery(window).scroll(function() {
  if (jQuery(window).scrollTop() > 300) {
    btn.addClass('show');
  } else {
    btn.removeClass('show');
  }
});

btn.on('click', function(e) {
  e.preventDefault();
  jQuery('html, body').animate({scrollTop:0}, '300');
});

jQuery(document).ready(function() {

    var owl = jQuery('#top-slider .owl-carousel');
    owl.owlCarousel({
    margin: 0,
    nav:false,
    autoplay : true,
    lazyLoad: true,
    autoplayTimeout: 5000,
    loop: true,
    dots: false,
    navText : ['<i class="fa fa-lg fa-chevron-left" aria-hidden="true"></i>','<i class="fa fa-lg fa-chevron-right" aria-hidden="true"></i>'],
    responsive: {
      0: {
        items: 1
      },
      576: {
        items: 1
      },
      768: {
        items: 1
      },
      1000: {
        items: 1
      },
      1200: {
        items: 1
      }
    },
    autoplayHoverPause : false,
    mouseDrag: true
  });

  var owl = jQuery('#best-sell .owl-carousel');
    owl.owlCarousel({
    margin: 20,
    nav:false,
    autoplay : true,
    lazyLoad: true,
    autoplayTimeout: 5000,
    loop: true,
    dots: false,
    navText : ['<i class="fa fa-lg fa-chevron-left" aria-hidden="true"></i>','<i class="fa fa-lg fa-chevron-right" aria-hidden="true"></i>'],
    responsive: {
      0: {
        items: 1
      },
      576: {
        items: 2
      },
      768: {
        items: 3
      },
      1000: {
        items: 4
      },
      1200: {
        items: 4
      }
    },
    autoplayHoverPause : false,
    mouseDrag: true
  });
})

window.addEventListener('load', (event) => {
  jQuery(".loading").delay(2000).fadeOut("slow");
});

jQuery(document).ready(function(){
  jQuery("button.cat-btn").click(function(){
    jQuery(".home_product_cat").toggle();
  });
});
