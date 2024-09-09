-- Insertar datos en la tabla Idioma
INSERT INTO Idioma (nombre, codigo_iso, alfabeto, region) VALUES
('Español', 'ESP', 'LATINO', 'España'),
('Inglés', 'GBR', 'LATINO', 'Reino Unido'),
('Francés', 'FRA', 'LATINO', 'Francia'),
('Alemán', 'DEU', 'LATINO', 'Alemania'),
('Italiano', 'ITA', 'LATINO', 'Italia'),
('Portugués', 'PTR', 'LATINO', 'Portugal'),
('Chino', 'CHN', 'CIRILICO', 'China'),
('Japonés', 'JPN', 'LATINO', 'Japón'),
('Árabe', 'SAU', 'ARABE', 'Arabia Saudita'),
('Ruso', 'RUS', 'CIRILICO', 'Rusia'),
('Coreano', 'KOR', 'COREANO', 'Corea del Sur'),
('Georgiano', 'GEO', 'GEORGIANO', 'Georgia'),
('Armenio', 'ARM', 'ARMENIO', 'Armenia');

-- Insertar datos en la tabla Palabra
INSERT INTO Palabra (palabra, descripcion, ejemplo_uso, nivel_dificultad, frecuencia_uso, fecha_creacion, idioma_id) VALUES
-- Español
('Hola', 'Saludo común', 'Hola, ¿cómo estás?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Español')),
('Casa', 'Edificio para vivir', 'Vivimos en una casa grande.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Español')),
('Amor', 'Sentimiento de afecto', 'El amor es importante en la vida.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Español')),

-- Inglés
('Hello', 'A common greeting', 'Hello, how are you?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Inglés')),
('House', 'Building for living', 'We live in a big house.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Inglés')),
('Love', 'Feeling of affection', 'Love is important in life.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Inglés')),

-- Francés
('Bonjour', 'Salutation courante', 'Bonjour tout le monde!', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Francés')),
('Maison', 'Bâtiment pour vivre', 'Nous vivons dans une grande maison.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Francés')),
('Amour', 'Sentiment d’affection', 'L’amour est important dans la vie.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Francés')),

-- Alemán
('Hallo', 'Allgemeine Begrüßung', 'Hallo, wie geht es dir?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Alemán')),
('Haus', 'Gebäude zum Wohnen', 'Wir wohnen in einem großen Haus.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Alemán')),
('Liebe', 'Gefühl der Zuneigung', 'Liebe ist wichtig im Leben.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Alemán')),

-- Italiano
('Ciao', 'Saluto comune', 'Ciao, come stai?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Italiano')),
('Casa', 'Edificio per abitare', 'Viviamo in una grande casa.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Italiano')),
('Amore', 'Sentimento di affetto', 'L’amore è importante nella vita.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Italiano')),

-- Portugués
('Olá', 'Saudação comum', 'Olá, como você está?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Portugués')),
('Casa', 'Edifício para viver', 'Vivemos em uma grande casa.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Portugués')),
('Amor', 'Sentimento de afeto', 'O amor é importante na vida.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Portugués')),

-- Chino
('你好', '常见问候', '你好，你怎么样?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Chino')),
('家', '居住的建筑', '我们住在一座大房子里。', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Chino')),
('爱', '情感的感受', '爱在生活中很重要。', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Chino')),

-- Japonés
('こんにちは', '一般的な挨拶', 'こんにちは、お元気ですか?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Japonés')),
('家', '住むための建物', '私たちは大きな家に住んでいます。', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Japonés')),
('愛', '感情的な感覚', '愛は人生で重要です。', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Japonés')),

-- Árabe
('مرحبا', 'تحية شائعة', 'مرحبا، كيف حالك؟', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Árabe')),
('منزل', 'بناء للعيش', 'نعيش في منزل كبير.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Árabe')),
('حب', 'شعور بالعاطفة', 'الحب مهم في الحياة.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Árabe')),

-- Ruso
('Здравствуйте', 'Обычное приветствие', 'Здравствуйте, как вы?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Ruso')),
('Дом', 'Здание для проживания', 'Мы живем в большом доме.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Ruso')),
('Любовь', 'Чувство привязанности', 'Любовь важна в жизни.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Ruso')),

-- Coreano
('안녕하세요', '일반적인 인사', '안녕하세요, 어떻게 지내세요?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Coreano')),
('집', '거주를 위한 건물', '우리는 큰 집에 살고 있습니다.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Coreano')),
('사랑', '감정의 느낌', '사랑은 삶에서 중요합니다.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Coreano')),

-- Georgiano
('გამარჯობა', 'ჩვეულებრივი greet', 'გამარჯობა, როგორ ხარ?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Georgiano')),
('სახლი', 'ცხოვრების შენობა', 'ჩვენ დიდ სახლში ვცხოვრობთ.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Georgiano')),
('სიყვარული', 'გრძნობითი გრძნობა', 'სიყვარული მნიშვნელოვანია ცხოვრებაში.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Georgiano')),


-- Armenio
('Բարև', 'Սովորական ողջույն', 'Բարև, ինչպես եք?', 1, 10, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Armenio')),
('Տուն', 'Գրող շինություն', 'Մենք ապրում ենք մեծ տանը.', 1, 9, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Armenio')),
('Սեր', 'Ամոթից զգացում', 'Սերը կարևոր է կյանքում.', 2, 8, '2024-09-09', (SELECT id FROM Idioma WHERE nombre = 'Armenio'));
