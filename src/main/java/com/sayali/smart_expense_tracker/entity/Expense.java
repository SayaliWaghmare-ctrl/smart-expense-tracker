package com.sayali.smart_expense_tracker.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    @Column(nullable = false, precision = 12, scale = 2)
	    private BigDecimal amount;

	    @ManyToOne
	    @JoinColumn(name = "category_id", nullable = false)
	    private Categories category;

	    @Column(name = "expense_date", nullable = false)
	    private LocalDate expenseDate;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "payment_method", nullable = false)
	    private PaymentMethod paymentMethod;

	    @Column(length = 500)
	    private String notes;

	    @Column(name = "created_at", nullable = false)
	    private LocalDate createdAt;

	    @Column(name = "updated_at")
	    private LocalDate updatedAt;

	    
	    // getters and setters	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public Categories getCategory() {
			return category;
		}

		public void setCategory(Categories category) {
			this.category = category;
		}

		public LocalDate getExpenseDate() {
			return expenseDate;
		}

		public void setExpenseDate(LocalDate expenseDate) {
			this.expenseDate = expenseDate;
		}

		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public LocalDate getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDate createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDate getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDate updatedAt) {
			this.updatedAt = updatedAt;
		}
	
}
