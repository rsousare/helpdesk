@echo off
echo    =============================
echo Passos para Configurar o Projeto:
echo    =============================
echo 1. Gere a chave JWT:
echo    - Execute a classe GenerateKey para gerar uma chave segura.
echo    - Copie a chave gerada e configure-a no application.properties:
echo      jwt.secret=suachavegerada
echo      jwt.expiration=180000
echo
echo 2. No Postman:
echo    - POST - http://localhost:8080/login
echo    - {
echo          "email": "vs@mail.pt",
echo          "senha": "123"
echo      }
echo    copiar o Token em:
echo    Authorization:  (ex de token) Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2c0BtYWlsLnB0IiwiZXhwIjoxNzE5NDk2MTY2fQ.HYsqcjHOBcmFoAsQXepQQz1jmdVqlg3rSqcFoJIXe70
echo
echo    e colar em:
echo    - GET - http://localhost:8080/chamados (ex)
echo    - Headers e acrescentar os campos:
echo        no Key: Authorization e no Value: o token copiado
echo.
echo ===========================
pause