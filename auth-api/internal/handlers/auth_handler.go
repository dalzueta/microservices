package handlers

import (
	"authapi/internal/auth"
	"encoding/json"
	"fmt"
	"net/http"
)

type AuthHandler struct {
	authService auth.AuthService
}

func NewAuthHandler(authService auth.AuthService) *AuthHandler {
	return &AuthHandler{authService: authService}
}

func (h *AuthHandler) Login(w http.ResponseWriter, r *http.Request) {
	var creds struct {
		Username string `json:"username"`
		Password string `json:"password"`
	}
	_ = json.NewDecoder(r.Body).Decode(&creds)

	valid, err := h.authService.Authenticate(creds.Username, creds.Password)
	if err != nil || !valid {
		http.Error(w, "invalid credentials", http.StatusUnauthorized)
		return
	}
	w.Write([]byte("login successful"))
}

func (h *AuthHandler) ValidateToken(w http.ResponseWriter, r *http.Request) {
	var req struct {
		Token     string `json:"token"`
		TokenType string `json:"token_type"`
	}
	authHeader := r.Header.Get("Authorization")
	fmt.Printf("Authorization Header: %s\n", authHeader)

	_ = json.NewDecoder(r.Body).Decode(&req)
	fmt.Println("el token en el handler es ", req.Token)
	valid, err := h.authService.ValidateToken(req.Token, req.TokenType)

	if err != nil || !valid {
		http.Error(w, "invalid token", http.StatusUnauthorized)
		return
	}
	w.WriteHeader(http.StatusOK)

}
