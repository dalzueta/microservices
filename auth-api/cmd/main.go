package main

import (
	"authapi/internal/auth"
	"authapi/internal/config"
	"authapi/internal/handlers"
	"authapi/internal/repository"
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/mux"
	"golang.org/x/crypto/bcrypt"
)

func main() {

	password := "mysecretpassword"
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		panic(err)
	}

	fmt.Println(string(hashedPassword))

	db := config.InitDB()
	userRepo := repository.NewUserRepository(db)
	authService := auth.NewAuthService(userRepo, "private-jwt_key")
	authHandler := handlers.NewAuthHandler(authService)

	router := mux.NewRouter()
	router.HandleFunc("/login", authHandler.Login).Methods("POST")
	router.HandleFunc("/validate-token", authHandler.ValidateToken).Methods("POST")

	log.Println("Server running on port 8080")
	http.ListenAndServe(":8080", router)
}
