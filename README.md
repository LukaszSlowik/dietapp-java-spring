# DietApp - Personalized Nutrition & Health Tracking Assistant

## Description

**DietApp** is an application designed to help people track their nutrition, calculate enzyme (Kreon) needs based on the fat content of meals, and support people with conditions like cystic fibrosis or diabetes. The app allows users to select meals, save them, and convert common kitchen units (such as teaspoons, tablespoons, cups, etc.) into grams for accurate calculations.

### Key Features:
- Personalized meal suggestions
- Meal tracking and fat content-based enzyme calculation
- Unit conversion (grams, teaspoons, tablespoons, cups, etc.)
- Support for conditions like cystic fibrosis and diabetes

---

## Configuration

### Environment Profiles

The application uses **Spring Profiles** to manage different configurations for various environments (e.g., development, production).

- **DEV**: Development environment configuration
- **PROD**: Production environment configuration

By default, Spring will load the settings based on the active profile.

### Local Configuration for Development

If you're working locally, you can customize the application settings by creating your own configuration file:

- **application-local.properties**: This file stores local configurations that should not be tracked by Git (e.g., local database credentials, test data).
- This file is **ignored by Git** to ensure sensitive information, such as passwords, is not exposed.

#### How to Set Up Local Configuration

1. **Create `application-local.properties`**

   In your local development environment, create a file named `application-local.properties` in the `src/main/resources` directory. This file can contain local settings that are specific to your development environment, such as database URLs or API keys.

   Example content:
   ```properties
   # application-local.properties
   myapp.database.url=jdbc:h2:mem:testdb
   myapp.api.key=your-local-api-key
