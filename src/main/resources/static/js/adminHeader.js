
$(document).ready(function () {
  if (typeof window.headerScriptsLoaded === 'undefined') {
    // Update the current date
    function updateDataDate() {
      const dateElement = document.getElementById('currentDataDate');
      if (dateElement) {
        const now = new Date();
        dateElement.textContent = `${String(now.getDate()).padStart(2, '0')}/${String(now.getMonth() + 1).padStart(2, '0')}/${now.getFullYear()}`;
      }
    }
    updateDataDate();

    // Update username display
    function updateUsernameDisplay() {
      const usernameDisplay = document.getElementById('headerUsernameDisplay');
      const username = sessionStorage.getItem('username') || 'Guest';
      if (usernameDisplay) usernameDisplay.textContent = username;
    }
    updateUsernameDisplay();

    // Sidebar toggle functionality
    const toggleBtn = document.getElementById('sidebarToggleBtnInHeader');
    const navLeft = document.querySelector('.nav-left-container');
    const mainContainer = document.querySelector('.main-container');

    if (toggleBtn && navLeft && mainContainer) {
      const isCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
      navLeft.classList.toggle('sidebar-collapsed', isCollapsed);
      mainContainer.classList.toggle('sidebar-collapsed', isCollapsed);
      mainContainer.classList.toggle('sidebar-active', !isCollapsed);

      toggleBtn.addEventListener('click', () => {
        const collapsed = navLeft.classList.toggle('sidebar-collapsed');
        mainContainer.classList.toggle('sidebar-collapsed', collapsed);
        mainContainer.classList.toggle('sidebar-active', !collapsed);
        localStorage.setItem('sidebarCollapsed', collapsed);
      });
    }

    window.headerScriptsLoaded = true;
  }
});
