# E-Commerce REST API

#### NOT: Proje henüz tamamlanmamıştır. Devam eden geliştirmeler ve iyileştirmeler yapılmaktadır.

Bu proje, Spring Boot kullanılarak geliştirilmiş bir e-ticaret API'sidir. JWT tabanlı kimlik doğrulama ve rol bazlı yetkilendirme sistemi içerir.

## Teknolojiler

- Java 23
- Spring Boot 3.4.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Maven
- Lombok

## Özellikler

- JWT tabanlı kimlik doğrulama
- Rol bazlı yetkilendirme (ADMIN, COURIER, CUSTOMER)
- Kullanıcı yönetimi
- Sipariş yönetimi
- Kurye yönetimi
- Ürün ve kategori yönetimi
- Sepet işlemleri

## Rol ve Yetkiler

### ADMIN
- Tüm kullanıcıları görüntüleme ve yönetme
- İstatistikleri görüntüleme
- Kullanıcı rollerini değiştirme
- Tüm sistem ayarlarına erişim

### COURIER
- Kendi teslimatlarını görüntüleme
- Teslimat durumunu güncelleme
- Kazanç bilgilerini görüntüleme

### CUSTOMER
- Sipariş oluşturma ve görüntüleme
- Profil yönetimi
- Sepet işlemleri
- Adres yönetimi

## API Endpoints

### Kimlik Doğrulama
```
POST /api/auth/register - Yeni kullanıcı kaydı
POST /api/auth/login - Kullanıcı girişi
```

### Admin Endpoints
```
GET /api/admin/users - Tüm kullanıcıları listeleme
GET /api/admin/statistics - İstatistikleri görüntüleme
POST /api/admin/users/{userId}/role - Kullanıcı rolünü değiştirme
```

### Kurye Endpoints
```
GET /api/courier/deliveries - Teslimatları görüntüleme
PUT /api/courier/status - Durum güncelleme
GET /api/courier/earnings - Kazançları görüntüleme
```

### Müşteri Endpoints
```
GET /api/customer/orders - Siparişleri görüntüleme
POST /api/customer/orders - Yeni sipariş oluşturma
GET /api/customer/profile - Profil görüntüleme
PUT /api/customer/profile - Profil güncelleme
```

## Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/yourusername/ecommerceAPI.git
```

2. PostgreSQL veritabanını oluşturun:
```sql
CREATE DATABASE ecommerce_db;
```

3. `application.properties` dosyasını düzenleyin:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Projeyi derleyin ve çalıştırın:
```bash
mvn clean install
mvn spring-boot:run
```

## API Kullanımı

### Kayıt Olma
```http
POST /api/auth/register
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123"
}
```

### Giriş Yapma
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "john@example.com",
    "password": "password123"
}
```

### API İstekleri
Tüm API isteklerinde JWT token'ı Authorization header'ında gönderilmelidir:
```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Güvenlik

- Tüm şifreler BCrypt ile şifrelenir
- JWT token'lar 24 saat geçerlidir
- Rol bazlı erişim kontrolü
- CSRF koruması
- XSS koruması


## DB Şeması
![diagram.png](src/main/resources/images/diagram.png)