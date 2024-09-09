package models

import "time"

type User struct {
	ID        uint      `gorm:"primaryKey"`
	Username  string    `gorm:"column:username"`
	Password  string    `gorm:"column:password"`
	Token     string    `gorm:"column:token"`
	TokenType string    `gorm:"column:token_type"`
	CreatedAt time.Time `gorm:"column:created_at"`
}

// TableName sets the table name for the User struct
func (User) TableName() string {
	return "user"
}
