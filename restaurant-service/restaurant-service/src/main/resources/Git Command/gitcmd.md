# Go to project root
cd "D:/Micro Service Project/food-ordering-app"

# Initialize git (only if not already done)
git init

# Add remote repo (only if not already added)
git remote add origin https://github.com/akashjena9208/food-ordering-app-microservices.git

# Check remote
git remote -v

# Pull latest changes to avoid push conflicts
git pull origin main --rebase

# Stage your first service (replace with actual service name)
git add restaurant-service/

# Commit with a clear message
git commit -m "Added restaurant-service initial setup"

# Push changes to remote
git push origin main

# ------------------------------
# Repeat for other services
# ------------------------------

# Stage next service
git add order-service/

# Commit changes
git commit -m "Added order-service initial setup"

# Pull remote changes first (to avoid conflicts)
git pull origin main --rebase

# Push changes
git push origin main
