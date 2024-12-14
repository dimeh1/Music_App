$(document).ready(function () {
    let elapsedTime = 0;  // Track the elapsed time in seconds
    const songDuration = 228;  // Duration of the song in seconds (3:48)
    let timer = null;  // Timer reference for controlling the time
    let isPlaying = false;  // Track whether the music is playing or paused
    let currentProgress = 0;  // Track the current progress of the progress bar

    // Function to format time from seconds to MM:SS
    function formatTime(seconds) {
        let minutes = Math.floor(seconds / 60);
        let sec = seconds % 60;
        sec = sec < 10 ? "0" + sec : sec;  // Ensure two-digit seconds
        return `${minutes}:${sec}`;
    }

    // Function to update the progress bar based on elapsed time
    function updateProgressBar() {
        const progress = (elapsedTime / songDuration) * 100;
        $(".progress-bar").css("width", progress + "%");
    }

    // Function to start the timer
    function startTimer() {
        if (!isPlaying) return;

        timer = setInterval(() => {
            if (elapsedTime < songDuration) {
                elapsedTime++;  // Increment the elapsed time by 1 second
                $(".start-time").text(formatTime(elapsedTime));  // Update the timer text
                updateProgressBar();  // Update the progress bar width
            } else {
                clearInterval(timer);  // Stop the timer once the song finishes
            }
        }, 1000);  // Update every second
    }

    // Function to pause the timer
    function pauseTimer() {
        clearInterval(timer);  // Clear the interval to stop updating the time
    }

    // Play/Pause button click event handler
    $(".play-button").on('click', function () {
        const $icon = $(this).find('.material-icons');

        if ($icon.text() === "pause") {
            // Pause the music
            $icon.text("play_arrow");  // Change icon to play-arrow
            pauseTimer();  // Pause the timer
            isPlaying = false;  // Set the play state to false
        } else {
            // Play the music
            $icon.text("pause");  // Change icon to pause
            isPlaying = true;  // Set the play state to true
            startTimer();  // Start the timer
        }
    });

    // Initialize progress bar and time at page load
    $(".start-time").text(formatTime(elapsedTime));  // Start time is 0:00 initially
    updateProgressBar();  // Initialize the progress bar at 0%
});

const audio = document.getElementById('audio');

function playSong(songPath) {
    const audio = document.getElementById('audioPlayer');

    // Play the audio
    function playAudio() {
        audio.play();
    }

    // Pause the audio
    function pauseAudio() {
        audio.pause();
    }
}
