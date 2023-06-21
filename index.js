const express = require('express');

const app = express();
const port = process.env.PORT || 3000; // Використовуйте змінну середовища PORT або 3000 як порт за замовчуванням

// Налаштування маршрутів

// Головна сторінка
app.get('/', (req, res) => {
    res.send('Hello, World!');
});

// Запуск сервера
app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});