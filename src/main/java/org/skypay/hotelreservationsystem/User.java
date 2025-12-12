package org.skypay.hotelreservationsystem;

public class User {
    private final int id;
    private int balance;

    public User(int id, int balance) {
        if (id <= 0) {
            throw new IllegalArgumentException("user id must be > 0");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("balance cannot be negative");
        }
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance cannot be negative");
        }
        this.balance = balance;
    }

    public void debit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("debit amount must be > 0");
        }
        if (amount > balance) {
            throw new IllegalStateException("insufficient balance");
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
