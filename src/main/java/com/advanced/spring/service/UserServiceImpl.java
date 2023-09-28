package com.advanced.spring.service;

import com.advanced.spring.dto.UserDTO;
import com.advanced.spring.entity.User;
import com.advanced.spring.exceptions.CustomException;
import com.advanced.spring.repository.UserJpaRepository;
import com.advanced.spring.utils.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
В JPA сутності мають ряд станів. Ось основні стани сутностей та їхній опис:

Transient (Тимчасовий):

Сутність щойно була створена за допомогою оператора new і ще не асоційована з EntityManager.
Якщо ви викличете persist() для такої сутності, вона перейде в стан "Managed".
Вона ще не має представлення в базі даних.
Managed (Керований або Persistent):

Сутність асоційована з EntityManager.
Будь-які зміни, внесені в таку сутність, будуть автоматично синхронізовані з базою даних при коміті транзакції (якщо є активна транзакція)
або при виклику flush() на EntityManager.
Якщо ви викличете remove() для такої сутності, вона перейде в стан "Removed".
Detached (Від'єднаний):

Сутність раніше була в стані "Managed", але зараз вона не асоційована з EntityManager.
Це може статися, наприклад, коли EntityManager закривається або якщо сутність була вилучена з кешу.
Якщо ви викличете merge() для такої сутності, вона знову стане "Managed" і буде синхронізована з базою даних.
Removed (Вилучена):

Сутність відмічена для видалення з бази даних.
Якщо транзакція здійснить коміт, сутність буде видалена з бази даних.
Щоб керувати станами сутностей, JPA використовує концепцію "життєвого циклу сутності",
і вона надає ряд операцій (наприклад, persist(), merge(), remove()) для керування переходами між цими станами.

 */
@Service
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserJpaRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            User user = userMapper.toEntity(userDTO);
            user.setId(id);
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        }
        // Тут можна додати обробку винятків, якщо елемент не знайдено.
        return null;
    }

    @Transactional(rollbackFor = CustomException.class, propagation = Propagation.REQUIRED)
    /*
    Атрибут rollbackFor в анотації @Transactional вказує на те, при яких винятках має відбутися відкат транзакції.
    Якщо ви хочете вказати конкретні винятки, для яких потрібно здійснити відкат, ви можете використовувати цей атрибут.

    За замовчуванням, анотація @Transactional ініціює відкат транзакції при будь-якому невідфільтрованому RuntimeException
    (тобто при виключеннях, які є підкласами RuntimeException

    Якщо ви вказали rollbackFor, то відкат буде здійснено тільки при виникненні вказаних виключень. (+ поведінка по дефолту)
    Якщо ви не вказали rollbackFor, тоді буде використовуватися поведінка за замовчуванням, і відкат буде здійснено при будь-якому RuntimeException.
    Якщо ви використовуєте rollbackFor разом із noRollbackFor, то noRollbackFor матиме вищий пріоритет.
    Це означає, що якщо виникає виключення, яке вказане в обох списках, відкату не буде.

    Тому, якщо ви вказали rollbackFor = CustomException.class, то транзакція буде відкочена тільки при виникненні CustomException,
    але буде також відкочена при виникненні будь-якого RuntimeException (якщо ви явно не вказали інше через noRollbackFor).
    -----------
    Propagation в контексті анотації @Transactional вказує на поведінку транзакції відносно інших транзакцій. Він допомагає визначити,
    як поточний метод взаємодіє з існуючою транзакцією (якщо вона є).

    Ось основні значення Propagation:

    REQUIRED (за замовчуванням):
    Якщо транзакція вже існує, то поточний метод буде виконуватися в межах цієї транзакції.
    Якщо транзакції немає, нова транзакція буде створена.

    SUPPORTS:
    Якщо транзакція вже існує, поточний метод буде виконуватися в межах цієї транзакції.
    Якщо транзакції немає, метод буде виконуватися поза транзакційним контекстом.

    MANDATORY:
    Якщо транзакція вже існує, поточний метод буде виконуватися в межах цієї транзакції.
    Якщо транзакції немає, буде кинутий виняток (IllegalTransactionStateException).

    REQUIRES_NEW:
    Поточний метод завжди буде виконуватися в новій транзакції.
    Якщо транзакція вже існує, вона буде призупинена до завершення методу з REQUIRES_NEW.

    NOT_SUPPORTED:
    Поточний метод завжди буде виконуватися поза транзакційним контекстом.
    Якщо транзакція вже існує, вона буде призупинена.

    NEVER:
    Поточний метод завжди буде виконуватися поза транзакційним контекстом.
    Якщо транзакція вже існує, буде кинутий виняток.

    NESTED:
    Якщо транзакція вже існує, виконання буде в межах "вкладеної" транзакції. Якщо головна транзакція завершується успішно,
    але вкладена транзакція має відкат, головна транзакція також буде відкочена.
    Якщо транзакції немає, її поведінка аналогічна REQUIRED.
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        throw new CustomException("deleted wrong should be rollback", 500);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    /*
    Анотація @Transactional з атрибутом readOnly = true вказує на те, що транзакція є тільки для читання,
    і вона не буде здійснювати зміни в базі даних. Але це не означає, що транзакція не буде відкрита на стороні Java.
    Транзакція буде відкрита, але буде оптимізована для операцій читання.

    Ось декілька ключових пунктів щодо readOnly = true:

    Оптимізація: Деякі постачальники постійності (JPA providers), наприклад Hibernate,
    можуть використовувати цю інформацію для виконання деяких внутрішніх оптимізацій,
    таких як відмова від внутрішнього зберігання змінених об'єктів (dirty checking).

    Відкат: Якщо метод, позначений як readOnly, спробує внести зміни в базу даних, ці зміни будуть відкинуті на завершення транзакції.

    Інші операційні оптимізації: Деякі бази даних або платформи могуть виконувати операції швидше, коли вони знають, що операція є тільки для читання.

    Не забезпечує ізоляції: readOnly = true не гарантує, що дані, які ви читаєте, не змінюються іншими транзакціями в той самий час.
    Це залежить від рівня ізоляції транзакції.

    Isolation.DEFAULT: Використовує рівень ізоляції, встановлений за замовчуванням у базі даних.
    Isolation.READ_UNCOMMITTED: Дозволяє транзакції читати не підтверджені зміни інших транзакцій.
    Isolation.READ_COMMITTED: Гарантує, що будь-яка читана даних була закомічена іншою транзакцією на момент початку поточної транзакції.
    Isolation.REPEATABLE_READ: Гарантує, що якщо рядок читається багато разів в межах однієї транзакції, результат буде однаковим.
    Isolation.SERIALIZABLE: Гарантує, що транзакції виконуються послідовно.
     */
    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
