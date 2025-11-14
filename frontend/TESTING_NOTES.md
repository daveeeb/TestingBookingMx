# Testing Notes (Frontend)

## Main Challenges
- Handling graph structure with invalid or missing cities.
- Ensuring all pure functions behaved consistently with different datasets.
- Dealing with edge cases (null inputs, negative distances, unknown nodes).

## Strategies Used
- Converted all functions to pure units to simplify Jest tests.
- Added strict validation on dataset load.
- Implemented sorting tests to ensure deterministic outputs.
- Used Jest coverage report to identify missing branches and conditions.

## Result
- Coverage achieved: >90%
- All tests passing.
