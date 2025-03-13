## Multiplatform PoC
This repository contains multiplatform proof of concept on both android and ios 
modular project structure and choices made for specifically
use of tech choices in those platform implementations
- Kotlin-Inject for dependency injection
- Ktorfit for networking
- DataStore for preferences/userdefaults
- Room for database/coredata
- Compose for ui
- BuildKonfig for config management in application
- data-domain-ui structure for each feature
- unit testing / compose ui testing on android (because it is better place to run there instead of running it on emulator)
and many more like `:core:injection` and `:core:injection:compiler` to accompany specific needs of multiplatform with `:conventions` project