package auth

import (
	"authapi/internal/repository"
	"encoding/base64"
	"errors"
	"fmt"
	"strings"

	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"
)

type AuthService interface {
	Authenticate(username, password string) (bool, error)
	ValidateToken(token string, tokenType string) (bool, error)
}

type authService struct {
	userRepo  repository.UserRepository
	jwtSecret string // Clave secreta para firmar/verificar JWT
}

func NewAuthService(userRepo repository.UserRepository, jwtSecret string) AuthService {
	return &authService{userRepo: userRepo, jwtSecret: jwtSecret}
}

func (s *authService) Authenticate(username, password string) (bool, error) {
	user, err := s.userRepo.FindByUsername(username)
	if err != nil {
		return false, err
	}
	err = bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(password))
	if err != nil {
		return false, errors.New("invalid password")
	}
	return true, nil
}

func (s *authService) ValidateToken(token string, tokenType string) (bool, error) {
	return s.validateBasicAuth(strings.TrimPrefix(token, "Basic "))
	// if strings.HasPrefix(token, "Bearer ") {
	// 	// Validar token JWT

	// 	return s.validateJWT(strings.TrimPrefix(token, "Bearer "))
	// } else if strings.HasPrefix(token, "Basic ") {
	// 	encodedCreds := strings.TrimPrefix(token, "Basic ")
	// 	fmt.Printf("Token codificado recibido: %s\n", encodedCreds)
	// 	// Validar token Basic
	// 	return s.validateBasicAuth(strings.TrimPrefix(encodedCreds, "Basic "))
	// } else {
	// 	return false, errors.New("unsupported token type")
	// }
}

// Validar tokens JWT
func (s *authService) validateJWT(tokenStr string) (bool, error) {
	token, err := jwt.Parse(tokenStr, func(token *jwt.Token) (interface{}, error) {
		// Validar que la firma sea mediante HMAC y devolver la clave secreta
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, errors.New("invalid signing method")
		}
		return []byte(s.jwtSecret), nil
	})

	if err != nil {
		return false, err
	}

	if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
		// Aquí puedes validar las claims, por ejemplo, el `sub` que es el usuario
		if username, ok := claims["sub"].(string); ok {
			// Si el token es válido, autentica al usuario
			return true, nil //s.userRepo.FindByUsername(username) != nil, nil
			fmt.Print(username)
		}
		return false, errors.New("invalid token claims")
	}

	return false, errors.New("invalid token")
}

// Validar tokens Basic
// func (s *authService) validateBasicAuth(encodedCreds string) (bool, error) {
// 	fmt.Println("desde validate basic: %s - password %s", encodedCreds)
// 	decoded, err := base64.StdEncoding.DecodeString(encodedCreds)
// 	fmt.Println("desde validate basic: %s - decodificada %s", decoded)
// 	// if err != nil {
// 	// 	fmt.Println("ERROR")
// 	// 	return false, errors.New("invalid basic auth encoding")
// 	// }
// 	fmt.Println(err)
// 	decodedString := string(decoded)
// 	fmt.Println("Token decodificado:", decodedString)
// 	fmt.Println("se va a obtener las credenciales")
// 	creds := strings.SplitN(string(decoded), ":", 2)
// 	if len(creds) != 2 {
// 		fmt.Println("ERROR 2")
// 		return false, errors.New("invalid basic auth format")
// 	}

// 	username, password := creds[0], creds[1]

// 	fmt.Println("desde validate basic: user : %s - password %s", username, password)
// 	// Validar el usuario y contraseña
// 	return s.Authenticate(username, password)
// }

func (s *authService) validateBasicAuth(encodedCreds string) (bool, error) {
	fmt.Printf("desde validate basic: encodedCreds: %s\n", encodedCreds)

	// Decodifica el token Base64

	decoded, err := base64.StdEncoding.DecodeString(encodedCreds)
	if err != nil {
		fmt.Println("Error al decodificar:", err)
		return false, errors.New("invalid basic auth encoding")
	}

	// Convierte el resultado a string
	decodedString := string(decoded)
	fmt.Printf("Token decodificado: %s\n", decodedString)

	// Separa las credenciales en usuario y contraseña
	creds := strings.SplitN(decodedString, ":", 2)
	if len(creds) != 2 {
		fmt.Println("Formato del token incorrecto")
		return false, errors.New("invalid basic auth format")
	}

	username, password := creds[0], creds[1]
	fmt.Printf("Usuario: %s - Contraseña: %s\n", username, password)

	// Valida el usuario y contraseña
	return s.Authenticate(username, password)
}
