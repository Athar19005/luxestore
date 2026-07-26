document.querySelectorAll('.dropdown').forEach(dropdown => {
    dropdown.addEventListener('mouseenter', () => dropdown.classList.add('open'));
    dropdown.addEventListener('mouseleave', () => dropdown.classList.remove('open'));
});

let currentIndex = 0;
const track = document.getElementById('feedbackTrack');
const dots = document.querySelectorAll('.dot');

function currentSlide(index) {
    currentIndex = index;
    if (track) {
        track.style.transform = `translateX(-${currentIndex * 100}%)`;
    }
    dots.forEach((dot, i) => {
        dot.classList.toggle('active', i === currentIndex);
    });
}

setInterval(() => {
    if (track) {
        currentIndex = (currentIndex + 1) % dots.length;
        currentSlide(currentIndex);
    }
}, 4000);

window.addEventListener('scroll', () => {
    const header = document.querySelector('header');
    if (header) {
        header.style.boxShadow = window.scrollY > 10 ? '0 4px 20px rgba(0,0,0,0.08)' : 'none';
    }
});