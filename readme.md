
# Test Task Effective Mobile

Приложение для просмотра и управления курсами. Позволяет пользователям просматривать список курсов, добавлять их в избранное и управлять своим профилем.

## Технологический стек

- **Язык**: Kotlin
- **UI**: Jetpack Compose
- **Навигация**: Jetpack Navigation Compose
- **DI**: Dagger Hilt
- **База данных**: Room
- **Сеть**: Retrofit + OkHttp
- **Архитектура**: MVVM + Clean Architecture


## Навигация

Приложение использует Jetpack Navigation Compose с следующими экранами:

- **Auth** - экран авторизации
- **Main** - главный экран со списком курсов
- **Favorite** - экран с избранными курсами
- **Account** - экран профиля пользователя
- **CourseDetail** - экран детальной информации о курсе (с параметром courseId)


##  База данных

Room database используется для хранения избранных курсов с таблицей `favorite_courses`:

- `courseId` (Primary Key) - ID курса
- `userId` - идентификатор пользователя
- `title` - название курса
- `description` - описание
- `imageUrl` - URL изображения
- `publishDate` - дата публикации
- `rating` - рейтинг
- `startDate` - дата начала
- `price` - цена
- `currency` - валюта
- `priceString` - цена в строковом формате

## Сетевой слой

- **Base URL**: `https://drive.usercontent.google.com/`
- **Эндпоинт**: `uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download`
- **Модели**: `CoursesResponse`, `CourseDto`


##  Функциональность

-  Просмотр списка курсов
-  Детальная информация о курсе
-  Добавление/удаление курсов в избранное
-  Авторизация пользователя
-  Навигация между экранами
-  Сохранение избранного в локальной БД

## Архитектурные решения

- **Clean Architecture** с разделением на модули по функциональности
- **Single Source of Truth** для данных через Room
- **StateFlow** для реактивного UI
- **Hilt** для внедрения зависимостей
- **ViewModels** с сохранением состояния при повороте экрана
