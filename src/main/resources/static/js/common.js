// Спільні функції для роботи з JWT та API

/**
 * Декодування JWT токену
 */
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64).split('').map(c =>
                '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
            ).join('')
        );
        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error('Error parsing JWT:', e);
        return null;
    }
}

/**
 * Вихід з системи
 */
function logout() {
    localStorage.removeItem('jwtToken');
    window.location.href = '/view/login';
}

/**
 * Виконання HTTP запиту з автентифікацією
 */
async function fetchWithAuth(url, options = {}) {
    const token = localStorage.getItem('jwtToken');

    if (!token) {
        window.location.href = '/view/login';
        return null;
    }

    const headers = {
        ...options.headers,
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };

    try {
        const response = await fetch(url, {
            ...options,
            headers
        });

        // Перевірка на неавторизований доступ
        if (response.status === 401) {
            localStorage.removeItem('jwtToken');
            window.location.href = '/view/login';
            return null;
        }

        return response;
    } catch (error) {
        console.error('Fetch error:', error);
        throw error;
    }
}

/**
 * Відображення алерту
 */
function showAlert(message, type, elementId = 'alertBox') {
    const alertBox = document.getElementById(elementId);
    if (!alertBox) return;

    alertBox.textContent = message;
    alertBox.className = `alert alert-${type} show`;

    setTimeout(() => {
        alertBox.className = 'alert';
    }, 5000);
}

/**
 * Відображення помилки поля форми
 */
function showFieldError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const error = document.getElementById(fieldId + 'Error');

    if (field) field.classList.add('error');
    if (error) {
        error.textContent = message;
        error.classList.add('show');
    }
}

/**
 * Очищення помилок форми
 */
function clearErrors() {
    document.querySelectorAll('.error-message').forEach(el => {
        el.classList.remove('show');
    });
    document.querySelectorAll('input, select').forEach(el => {
        el.classList.remove('error');
    });
}

/**
 * Перевірка чи токен валідний
 */
function isTokenValid() {
    const token = localStorage.getItem('jwtToken');
    if (!token) return false;

    const userData = parseJwt(token);
    if (!userData) return false;

    // Перевірка терміну дії
    const currentTime = Math.floor(Date.now() / 1000);
    if (userData.exp && userData.exp < currentTime) {
        localStorage.removeItem('jwtToken');
        return false;
    }

    return true;
}

/**
 * Отримання даних користувача з токену
 */
function getUserFromToken() {
    const token = localStorage.getItem('jwtToken');
    if (!token) return null;

    return parseJwt(token);
}

/**
 * Перевірка чи користувач має роль адміністратора
 */
function isAdmin() {
    const userData = getUserFromToken();
    if (!userData) return false;

    return userData.roles && (
        userData.roles.includes('ROLE_ADMIN') ||
        userData.roles.includes('ADMIN')
    );
}

/**
 * Валідація email
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Форматування дати
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('uk-UA', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    });
}

/**
 * Форматування часу
 */
function formatDateTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('uk-UA', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}