package com.example.todoapp.observe;

import com.example.todoapp.data.NoteCard;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers; // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }
    // У нас будет разовый наблюдатель, и мы каждый раз будем его регистрировать, поскольку фрагмент
    // будет создаваться каждый раз при редактировании записи. После отсылки сообщения будем
    // отписывать наблюдатель

    // Подписать:
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать:
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие:
    public void notifySingle(NoteCard noteCard) {
        for (Observer observer : observers) {
            observer.updateNoteCard(noteCard);
            unsubscribe(observer);
        }
    }

}
