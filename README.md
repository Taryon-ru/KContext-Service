KContext Service

Фрагменты реализации KContext Service — части платформы Constructum
(название платформы не придумано, пока),
отвечающей за контекст и управление доменными объектами.

Репозиторий иллюстрирует книгу «От объекта к системе»:
Как модель реального мира определяет архитектуру цифровых платформ.

Репозиторий книги: https://github.com/Taryon-ru/from-object-to-system

---
О репозитории

Здесь опубликованы отдельные фрагменты реального кода KContext Service.
Цель — показать, как идеи из книги реализуются в конкретных классах.

Это не проект, не фреймворк и не готовый продукт.
Здесь нет инфраструктуры, миграций, конфигурации, тестов
и интеграционного слоя.

---
### Snapshot 01 — Object Foundation

Дата: 2026-08-12

Базовая объектная модель:

- KAnchor
- KObject
- KAttribute / KAttributeKey
- KSystemAttributes / KObjectAttributes

Ключевая идея — разделение между самим объектом и сведениями о нём:

KAnchor → KObject → KObjectAttributes → KAttribute

---

### Snapshot 02 — Object Context

Расширяем объектную модель контекстом, связанным с объектом.

Добавлены::

- KContext
- object context ownership
- context lifecycle
- the relationship between KObject and KContext
- the separation between object identity and its administrative context

---
### Releases

- [Snapshot 01](../../releases/tag/snapshot-01)
- [Snapshot 02 — Object Context](../../releases/tag/snapshot-02-object-context)

---

Ограничения

- Может не компилироваться. Зависимости могут быть не определены.
- Состав кода может отличаться от версии к версии.
- Может существенно отличаться от текущей реализации сервиса.
- Поддержка и сопровождение не ведутся (ну, или почти).

---
Лицензия

Не предназначено для production-использования. Не поддерживается
как самостоятельный продукт или reference-реализация.

Репозиторий распространяется под PolyForm Noncommercial License 1.0.0:
https://polyformproject.org/licenses/noncommercial/1.0.0

Коммерческое использование — по отдельному соглашению
с правообладателем. Подробности в LICENSE.

Copyright © 2026 

---

In English

KContext Service

Fragments of the KContext Service implementation — part of the
Constructum platform (platform name still in the works), which handles
context and governance of domain objects.

This repository illustrates the book "From Object to System":
How a model of the real world determines the architecture
of digital platforms.

Book repository: https://github.com/Taryon-ru/from-object-to-system

---

About

This repo contains fragments of the actual KContext Service code.
The idea is to show how the book's concepts end up in real classes.

It's not a project, framework, or finished product.
No infrastructure, migrations, config, tests, or integration layer here.

---

Snapshot 01 — Object Foundation

Date: 2026-08-12

Basic object model:

- KAnchor
- KObject
- KAttribute / KAttributeKey
- KSystemAttributes / KObjectAttributes

The core idea is separating the object itself from what's known about it:

KAnchor → KObject → KObjectAttributes → KAttribute

---

Limitations

- May not compile. Dependencies may be unresolved.
- Code composition may vary between versions.
- May differ substantially from the current service implementation.
- No support or maintenance (well, almost).

---

License

Not meant for production use. Not maintained as a standalone product
or reference implementation.

Licensed under PolyForm Noncommercial License 1.0.0:
https://polyformproject.org/licenses/noncommercial/1.0.0

Commercial use requires a separate agreement with the copyright holder.
See LICENSE for details.

Copyright © 2026 
