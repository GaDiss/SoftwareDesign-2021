## Проектирование ПО - Лабораторная работа 6 - VisitorCalculator

Цель: получить практический опыт применения паттернов поведения visitor и state.

Необходимо реализовать калькулятор, который умеет преобразовывать простые арифметические выражения в обратную польскую запись (ОПЗ) и вычислять их. Пример выражения:

(23 + 10) * 5 – 3 * (32 + 5) * (10 – 4 * 5) + 8 / 2.

Выражение может содержать скобки, пробельные символы, цифры и 4 операции: +, -, *, /.