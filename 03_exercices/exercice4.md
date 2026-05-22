
# Exercice Password

Créer une classe `PasswordValidator`.

Un mot de passe est valide si :

1. il n’est pas `null` ;
2. il contient au moins 8 caractères ;
3. il contient au moins une lettre minuscule ;
4. il contient au moins une lettre majuscule ;
5. il contient au moins un chiffre ;
6. il contient au moins un caractère spécial parmi `!`, `@`, `#`, `$`, `%`.

La classe doit exposer deux méthodes :

```java
boolean isValid(String password)
String getErrorMessage(String password)
```

Règles attendues :

| Mot de passe | Résultat attendu |
| ------------ | ---------------- |
| `Password1!` | valide           |
| `Admin2024@` | valide           |
| `short1!`    | invalide         |
| `PASSWORD1!` | invalide         |
| `password1!` | invalide         |
| `Password!`  | invalide         |
| `Password1`  | invalide         |
| `null`       | invalide         |

Messages attendus :

| Cas                      | Message                                                |
| ------------------------ | ------------------------------------------------------ |
| `null`                   | `Password must not be null`                            |
| moins de 8 caractères    | `Password must contain at least 8 characters`          |
| pas de minuscule         | `Password must contain at least one lowercase letter`  |
| pas de majuscule         | `Password must contain at least one uppercase letter`  |
| pas de chiffre           | `Password must contain at least one digit`             |
| pas de caractère spécial | `Password must contain at least one special character` |
| valide                   | `Password is valid`                                    |

Travail demandé :

- créer la classe `PasswordValidator` ;
- créer des tests classiques ;
- créer des tests paramétrés avec `@CsvSource` ;
- créer un test avec `@ValueSource` ;
- créer un test avec `@MethodSource` ;
- vérifier les messages d’erreur (Messages attendus);
- générer le rapport JaCoCo (screen a envoyer).

Bonus :
- créer un test avec `@NullAndEmptySource` ;