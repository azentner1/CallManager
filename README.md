# App for processing incoming calls (and SMS)

Features:
- Categorize phone numbers: Trusted, Suspicious, Blocked. Trusted numbers will ring thru normally, suspicious numbers will display a warning while numbers marked as "blocked" will not go through.
- SMS blocking (just logging atm). SMS from "blocked" phone number will be logged or if they contain a word from the list of blocked words.
- Configurable word blocking list for SMS blocking
- Block all phone numbers that are not in your devices contact list

Known issues:
- SMS "blocking" not working if the app is not set as default messaging app. Currently it's only logging blocked SMS messages.
