

# KContext Service

Фрагменты реализации KContext Service — части платформы Constructum
(название платформы не придумано, пока),
отвечающей за контекст и управление доменными объектами.

Репозиторий иллюстрирует книгу «От объекта к системе»:
Как модель реального мира определяет архитектуру цифровых платформ.

Репозиторий книги: https://github.com/Taryon-ru/from-object-to-system

---

## О репозитории

Здесь опубликованы отдельные фрагменты реального кода KContext Service.
Цель — показать, как идеи из книги реализуются в конкретных классах.

Это не проект, не фреймворк и не готовый продукт.
Здесь нет инфраструктуры, миграций, конфигурации, тестов
и интеграционного слоя.

---

## Возможное применение

KContext Service предназначен для управления доменными объектами,
их контекстами, связями и правами доступа.

Такая модель может использоваться не только внутри PLM-систем,
но и как отдельный слой управления доступом для систем,
работающих с доменными данными.

Одним из возможных применений является RAG.

RAG-сервис может отвечать за загрузку документов, разбиение на фрагменты,
создание embeddings и поиск, в то время как KContext Service может
отвечать за определение того, какие объекты доступны конкретному
пользователю.

Например:

```text
Document
   │
   ├── chunk
   ├── chunk
   └── chunk
        │
        └── objectId
               │
               ▼
        KContext Service
               │
               ▼
        access decision
````

При этом vector store не обязан становиться источником правил
авторизации.

Упрощённо поток retrieval может выглядеть так:

```text
user query
    ↓
vector / semantic search
    ↓
candidate object IDs
    ↓
authorization check
    ↓
authorized content
    ↓
LLM context
```

RAG-сервис при этом остаётся ответственным за retrieval и генерацию,
а KContext Service — за идентичность объектов, их контекст и права
доступа.

Важно, что решение о доступе принимается до передачи содержимого
языковой модели. LLM не должна сама определять, имеет ли пользователь
право видеть найденные данные.

Это возможный сценарий применения модели, представленной в репозитории,
а не утверждение о наличии готовой RAG-интеграции.

---

### Snapshot 01 — Object Foundation

Дата: 2026-08-12

Базовая объектная модель:

* KAnchor
* KObject
* KAttribute / KAttributeKey
* KSystemAttributes / KObjectAttributes

Ключевая идея — разделение между самим объектом и сведениями о нём:

KAnchor → KObject → KObjectAttributes → KAttribute

---

### Snapshot 02 — Object Context

Расширяем объектную модель контекстом, связанным с объектом.

Добавлены:

* KContext
* object context ownership
* context lifecycle
* the relationship between KObject and KContext
* the separation between object identity and its administrative context

---

### Snapshot 03 — Entity

Расширяем модель конкретными доменными сущностями.

Добавлены:

* KUser
* AKUserLogin
* AKUserFirstName
* KGroup
* AKGroupName
* AKGroupParent
* KProject
* AKProjectName

Snapshot показывает, как базовая объектная модель применяется
для представления конкретных доменных объектов и их атрибутов.

---

### Snapshot 04 — Link

Расширяем модель связями между доменными объектами.

Добавлены:

- KLink
- KLinkAttributes
- KLinkUserGroup
- AKLnkUserGroupData
- AKLinkClosedBy

Snapshot показывает, как связь представляется отдельно от самих
доменных объектов и их контекстов.

KLink не является KObject и не получает KContext.
Первая конкретная реализация — связь между пользователем и группой:

KUser ← KLinkUserGroup → KGroup

Связь имеет собственный жизненный цикл и собственные атрибуты.
При этом идентичность объектов, участвующих в связи, остаётся
независимой от самой связи.

---

### Releases

* [Snapshot 01](../../releases/tag/snapshot-01)
* [Snapshot 02 — Object Context](../../releases/tag/snapshot-02-object-context)
* [Snapshot 03 — Entity](../../releases/tag/snapshot-03-entity)
* [Snapshot 04 — Link](../../releases/tag/snapshot-04-link)

---

## Ограничения

* Может не компилироваться. Зависимости могут быть не определены.
* Состав кода может отличаться от версии к версии.
* Может существенно отличаться от текущей реализации сервиса.
* Поддержка и сопровождение не ведутся (ну, или почти).

---

## Лицензия

Не предназначено для production-использования. Не поддерживается
как самостоятельный продукт или reference-реализация.

Репозиторий распространяется под PolyForm Noncommercial License 1.0.0:
[https://polyformproject.org/licenses/noncommercial/1.0.0](https://polyformproject.org/licenses/noncommercial/1.0.0)

Коммерческое использование — по отдельному соглашению
с правообладателем. Подробности в LICENSE.

Copyright © 2026

---

# In English

## KContext Service

Fragments of the KContext Service implementation — part of the
Constructum platform (platform name still in the works), which handles
context and governance of domain objects.

This repository illustrates the book "From Object to System":
How a model of the real world determines the architecture
of digital platforms.

Book repository: [https://github.com/Taryon-ru/from-object-to-system](https://github.com/Taryon-ru/from-object-to-system)

---

## About

This repo contains fragments of the actual KContext Service code.
The idea is to show how the book's concepts end up in real classes.

It's not a project, framework, or finished product.
No infrastructure, migrations, config, tests, or integration layer here.

---

## Possible Applications

KContext Service is designed to manage domain objects,
their contexts, relationships, and access control.

The same model can be used not only inside PLM systems,
but also as an authorization layer for systems
that operate on domain data.

One possible application is RAG.

A RAG service can remain responsible for document ingestion,
chunking, embeddings, and retrieval, while KContext Service
determines which objects are accessible to a particular user.

For example:

```text
Document
   │
   ├── chunk
   ├── chunk
   └── chunk
        │
        └── objectId
               │
               ▼
        KContext Service
               │
               ▼
        access decision
```

The vector store does not have to become the source of
authorization rules.

A simplified retrieval flow can look like this:

```text
user query
    ↓
vector / semantic search
    ↓
candidate object IDs
    ↓
authorization check
    ↓
authorized content
    ↓
LLM context
```

The RAG service remains responsible for retrieval and generation,
while KContext Service remains responsible for object identity,
context, and access control.

The important boundary is that authorization is resolved before
content is provided to the language model. The LLM should not decide
whether a user is allowed to access the retrieved data.

This is a possible application of the model demonstrated in this
repository, not a claim that a complete RAG integration is already
implemented.

---

### Snapshot 01 — Object Foundation

Date: 2026-08-12

Basic object model:

* KAnchor
* KObject
* KAttribute / KAttributeKey
* KSystemAttributes / KObjectAttributes

The core idea is separating the object itself from what's known about it:

KAnchor → KObject → KObjectAttributes → KAttribute

---

### Snapshot 02 — Object Context

Expanding the object model with a context associated with the object.

Added:

* KContext
* object context ownership
* context lifecycle
* the relationship between KObject and KContext
* the separation between object identity and its administrative context

---

### Snapshot 03 — Entity

Extending the model with concrete domain entities.

Added:

* KUser
* AKUserLogin
* AKUserFirstName
* KGroup
* AKGroupName
* AKGroupParent
* KProject
* AKProjectName

The snapshot shows how the basic object model is applied
to concrete domain objects and their attributes.

---

### Snapshot 04 — Link

Expanding the model with relationships between domain objects.

Added:

- KLink
- KLinkAttributes
- KLinkUserGroup
- AKLnkUserGroupData
- AKLinkClosedBy

The snapshot shows how a relationship is represented separately
from the domain objects themselves and their contexts.

KLink is not a KObject and does not receive a KContext.
The first concrete implementation is a relationship between a user
and a group:

KUser ← KLinkUserGroup → KGroup

The relationship has its own lifecycle and attributes.
At the same time, the identity of the objects participating in the
relationship remains independent of the relationship itself.

---

### Releases

* [Snapshot 01](../../releases/tag/snapshot-01)
* [Snapshot 02 — Object Context](../../releases/tag/snapshot-02-object-context)
* [Snapshot 03 — Entity](../../releases/tag/snapshot-03-entity)
* [Snapshot 04 — Link](../../releases/tag/snapshot-04-link)

---

## Limitations

* May not compile. Dependencies may be unresolved.
* Code composition may vary between versions.
* May differ substantially from the current service implementation.
* No support or maintenance (well, almost).

---

## License

Not meant for production use. Not maintained as a standalone product
or reference implementation.

Licensed under PolyForm Noncommercial License 1.0.0:
[https://polyformproject.org/licenses/noncommercial/1.0.0](https://polyformproject.org/licenses/noncommercial/1.0.0)

Commercial use requires a separate agreement with the copyright holder.
See LICENSE for details.

Copyright © 2026



