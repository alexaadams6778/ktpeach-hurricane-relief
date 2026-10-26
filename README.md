# KTPeach Hurricane Relief System

KTPeach Hurricane Relief System is a Java application designed to help communities coordinate hurricane-relief efforts. The system supports affected residents, volunteers, shelter managers, and system administrators by organizing help requests, volunteer assignments, shelter availability, emergency contacts, and hurricane-related information.

The project demonstrates a complete relief scenario: the Carter family creates a request for transportation and medical support, Rosa Delgado is matched as a transportation volunteer, the system recommends a suitable shelter, trip information is exported, the family checks in, and Shelter Manager Felix Vance completes the request.

## Walkthrough Video

YouTube Link: [youtube.com/watch?v=iax2gTID8e8](https://www.youtube.com/watch?v=iax2gTID8e8)

## Features

- Create and manage hurricane-relief help requests
- Match volunteers to requests using skills and nearby locations
- Track volunteer vehicle information, certifications, and assigned requests
- Find suitable shelters based on capacity, pet policy, and medical-staff availability
- Check families and pets into shelters
- Export volunteer trip information and driving directions
- Enforce role-based permissions for completing requests
- Save and load users, shelters, and requests using JSON files

## Project Design

The project uses several object-oriented design patterns and principles:

- **Singleton pattern:** `UserList`, `RequestList`, `ShelterList`, `HurricaneReliefSystem`, and `MapTools` each provide one shared system-wide instance.
- **Facade pattern:** `HurricaneReliefSystem` coordinates major system actions, including login, request management, shelter searching, volunteer assignment, and data saving.
- **Inheritance:** `Victim`, `Volunteer`, `SystemAdmin`, and `ShelterManager` extend the abstract `User` class.

## Running the Project

### Requirements

- Java 17
- Maven
- A map API key stored locally as `MAP_API_KEY`

Create a local `.env` file in the project root:

```text
MAP_API_KEY=your_api_key_here
