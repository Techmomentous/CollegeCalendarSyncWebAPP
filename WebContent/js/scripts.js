
var PORT = "8080";
var BASE_URL = "CollegeCalendar";
/* Portfolio */
$(window).load(function() {
	var $cont = $('.portfolio-group');

	$cont.isotope({
		itemSelector : '.portfolio-group .portfolio-item',
		masonry : {
			columnWidth : $('.isotope-item:first').width(),
			gutterWidth : -20,
			isFitWidth : true
		},
		filter : '*',
	});

	$('.portfolio-filter-container a').click(function() {
		$cont.isotope({
			filter : this.getAttribute('data-filter')
		});

		return false;
	});

	var lastClickFilter = null;
	$('.portfolio-filter a').click(function() {

		// first clicked we don't know which element is selected last time
		if (lastClickFilter === null) {
			$('.portfolio-filter a').removeClass('portfolio-selected');
		} else {
			$(lastClickFilter).removeClass('portfolio-selected');
		}

		lastClickFilter = this;
		$(this).addClass('portfolio-selected');
	});

});

/* Image Hover - Add hover class on hover */
$(document).ready(function() {
	if (Modernizr.touch) {
		// show the close overlay button
		$(".close-overlay").removeClass("hidden");
		// handle the adding of hover class when clicked
		$(".image-hover figure").click(function(e) {
			if (!$(this).hasClass("hover")) {
				$(this).addClass("hover");
			}
		});
		// handle the closing of the overlay
		$(".close-overlay").click(function(e) {
			e.preventDefault();
			e.stopPropagation();
			if ($(this).closest(".image-hover figure").hasClass("hover")) {
				$(this).closest(".image-hover figure").removeClass("hover");
			}
		});
	} else {
		// handle the mouseenter functionality
		$(".image-hover figure").mouseenter(function() {
			$(this).addClass("hover");
		})
		// handle the mouseleave functionality
		.mouseleave(function() {
			$(this).removeClass("hover");
		});
	}

	// Sidebar Toggle Function
	var trigger = $('.hamburger'), overlay = $('.overlay'), isClosed = false;

	trigger.click(function() {
		hamburger_cross();
	});

	function hamburger_cross() {

		if (isClosed == true) {
			overlay.hide();
			trigger.removeClass('is-open');
			trigger.addClass('is-closed');
			isClosed = false;
		} else {
			overlay.show();
			trigger.removeClass('is-closed');
			trigger.addClass('is-open');
			isClosed = true;
		}
	}

	$('[data-toggle="offcanvas"]').click(function() {
		$('#wrapper').toggleClass('toggled');
	});
});

// thumbs animations
$(function() {

	$(".thumbs-gallery i").animate({
		opacity : 0

	}, {
		duration : 300,
		queue : false
	});

	$(".thumbs-gallery").parent().hover(function() {
	}, function() {
		$(".thumbs-gallery i").animate({
			opacity : 0
		}, {
			duration : 300,
			queue : false
		});
	});

	$(".thumbs-gallery i").hover(function() {
		$(this).animate({
			opacity : 0

		}, {
			duration : 300,
			queue : false
		});

		$(".thumbs-gallery i").not($(this)).animate({
			opacity : 0.4
		}, {
			duration : 300,
			queue : false
		});
	}, function() {
	});

});

// Mobile Menu
$(function() {
	$('#hornavmenu').slicknav();
});

// Sticky Divs
// $('#header').affix({
// offset: {
// top:42
// }
// });
// $('#hornav').affix({
// offset: {
// top:42
// }
// });

$(window).load(function() {
	$("#hornav").sticky({
		topSpacing : 120
	});
});
$(window).load(function() {
	$("#header").sticky({
		topSpacing : 0
	});
});

//---------------------------------------------------
//---------------HELPER FUNCTIONS-------------------
//---------------------------------------------------
function getUniqueID() {
	return Math.ceil(Math.random() * 100);
}
function getRandomColor() {
	var letters = '0123456789ABCDEF';
	var color = '#';
	for (var i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}