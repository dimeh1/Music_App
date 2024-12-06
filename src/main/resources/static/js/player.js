$(".play-button").on('click', function() {
    // Use .find() to locate the <i> element within the clicked button
    const $icon = $(this).find('.material-icons');

    // Check the current text and toggle it
    if ($icon.text() === "pause") {
        $icon.text("play_arrow");
    } else {
        $icon.text("pause");
    }
});
